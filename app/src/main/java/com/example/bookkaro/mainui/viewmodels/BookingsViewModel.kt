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

class FirestoreRepository(private val application: Application) {
    private var firestoreDB = FirebaseFirestore.getInstance()

    fun getBookings(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_order_data))
    }

    fun getShops(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_vendor_data))
    }

}

class BookingsViewModel(private val application: Application) : ViewModel() {

    private val TAG = "BOOKINGS_VIEW_MODEL"

    private val firestoreRepository = FirestoreRepository(application)
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
                Log.e(TAG, "Change occurred: ${doc.data}")
                val status = doc.getLong(application.getString(R.string.firestore_collection_order_data_field_status))
                        ?: Booking.STATUS_PENDING
                val shopId = doc.getString(application.getString(R.string.firestore_collection_order_data_field_accepted_shop_id))
                        ?: ""
                if (status == Booking.STATUS_PENDING) {
                    val booking = Booking(
                            doc.id,
                            doc.getDate(application.getString(R.string.firestore_collection_order_data_field_service_date))!!,
                            doc.getString(application.getString(R.string.firestore_collection_order_data_field_service_name))!!,
                            doc.getLong(application.getString(R.string.firestore_collection_order_data_field_service_price))!!,
                            status,
                            shopId,
                            null,
                            null,
                            null)
                    bookingsList.add(booking)
                    bookingsList.sortByDescending { it.serviceDate }
                    bookings.value = bookingsList
                } else {
                    firestoreRepository.getShops().document(shopId).get()
                            .addOnSuccessListener { shopData ->
                                val booking = Booking(
                                        doc.id,
                                        doc.getDate(application.getString(R.string.firestore_collection_order_data_field_service_date))!!,
                                        doc.getString(application.getString(R.string.firestore_collection_order_data_field_service_name))!!,
                                        doc.getLong(application.getString(R.string.firestore_collection_order_data_field_service_price))!!,
                                        status,
                                        shopId,
                                        shopData.getString(application.getString(R.string.firestore_collection_vendor_data_field_shop_icon_url))!!,
                                        shopData.getString(application.getString(R.string.firestore_collection_vendor_data_field_shop_name))!!,
                                        shopData.getString(application.getString(R.string.firestore_collection_vendor_data_field_shop_address))!!)
                                bookingsList.add(booking)
                                bookingsList.sortByDescending { it.serviceDate }
                                bookings.value = bookingsList
                            }
                            .addOnFailureListener {
                                Log.e(TAG, "Failed to fetch shop data")
                                it.printStackTrace()
                            }
                }
            }
        }
        return bookings
    }
}

class BookingsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookingsViewModel(application) as T
    }
}