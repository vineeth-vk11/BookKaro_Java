package com.example.bookkaro.mainui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.helper.Booking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class BookingsRepository(val application: Application) {
    private var firestoreDB = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid

    fun getBookings(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_user_data))
                .document(uid)
                .collection(application.getString(R.string.firestore_sub_collection_bookings))
    }
}

class BookingsViewModel(private val application: Application) : ViewModel() {

    val TAG = "BOOKINGS_VIEW_MODEL"

    private val bookingsRepository = BookingsRepository(application)
    private var bookings: MutableLiveData<List<Booking>> = MutableLiveData()

    fun getBookings(): LiveData<List<Booking>> {

        bookingsRepository.getBookings().orderBy(application.getString(R.string.firestore_sub_collection_bookings_field_service_date), Query.Direction.DESCENDING).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore listening failed.")
                bookings.value = null
                return@addSnapshotListener
            }

            val bookingsList: MutableList<Booking> = mutableListOf()
            for (doc in querySnapshot!!) {
                    val booking = Booking(
                            doc.id,
                            doc.getString(application.getString(R.string.firestore_sub_collection_bookings_field_shop_icon)),
                            doc.getString(application.getString(R.string.firestore_sub_collection_bookings_field_shop_name))!!,
                            doc.getDate(application.getString(R.string.firestore_sub_collection_bookings_field_service_date))!!,
                            doc.getString(application.getString(R.string.firestore_sub_collection_bookings_field_shop_address))!!,
                            doc.getLong(application.getString(R.string.firestore_sub_collection_bookings_field_service_price))!!,
                            doc.getString(application.getString(R.string.firestore_sub_collection_bookings_field_service_name))!!,
                            doc.getLong(application.getString(R.string.firestore_sub_collection_bookings_field_service_status))!!)
                    bookingsList.add(booking)
                }

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