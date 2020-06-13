package com.example.bookkaro.mainui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.helper.bookings.Booking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class BookingsRepository(private val application: Application) {
    private var firestoreDB = FirebaseFirestore.getInstance()

    fun getBookings(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_order_data))
    }

}

class BookingsViewModel(private val application: Application) : ViewModel() {

    private val TAG = "BOOKINGS_VIEW_MODEL"

    private val firestoreRepository = BookingsRepository(application)
    private var bookings: MutableLiveData<List<Booking>> = MutableLiveData()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    fun getBookings(): LiveData<List<Booking>> {

        firestoreRepository.getBookings().whereEqualTo(application.getString(R.string.firestore_collection_order_data_field_user_id), uid).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore listening failed.")
                bookings.value = null
                return@addSnapshotListener
            }

            val bookingsList: MutableList<Booking> = mutableListOf()
            for (doc in querySnapshot!!) {
                bookingsList.add(Booking(
                        doc.id,
                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_accepted_shop_number)),
                        doc.getDate(application.getString(R.string.firestore_collection_order_data_field_service_date))!!,
                        doc.getLong(application.getString(R.string.firestore_collection_order_data_field_service_type))!!,
                        doc.getLong(application.getString(R.string.firestore_collection_order_data_field_service_category))!!,
                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_service_name))!!,
                        doc.getLong(application.getString(R.string.firestore_collection_order_data_field_service_price))!!,
                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_shop_address)),
                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_shop_icon_url)),
                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_shop_name)),
                        doc.getLong(application.getString(R.string.firestore_collection_order_data_field_status))!!,
                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_user_id))!!))
            }
            bookingsList.sortByDescending { it.serviceDate }
            bookings.value = bookingsList
        }
        return bookings
    }
}

class BookingsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookingsViewModel(application) as T
    }
}