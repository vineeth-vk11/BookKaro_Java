package com.example.bookkaro.mainui.makebooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.helper.bookings.Booking
import com.google.firebase.firestore.FirebaseFirestore

class OrderPlacedViewModel(private val docId: String) : ViewModel() {

    private val orderStatus: MutableLiveData<Long> = MutableLiveData(Booking.STATUS_PENDING)
    fun checkOrderStatus(): LiveData<Long> {
        FirebaseFirestore.getInstance().collection("OrderData").document(docId).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            orderStatus.value = documentSnapshot?.getLong("status") ?: Booking.STATUS_PENDING
        }
        return orderStatus
    }

    fun getOrderStatusString(orderStatus: Long): Int {
        return when (orderStatus) {
            Booking.STATUS_ACCEPTED -> R.string.status_accepted
            Booking.STATUS_STARTED -> R.string.status_started
            Booking.STATUS_COMPLETED -> R.string.status_completed
            Booking.STATUS_CANCELED -> R.string.status_canceled
            else -> R.string.status_pending
        }
    }
}


class OrderPlacedViewModelFactory(private val docId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderPlacedViewModel(docId) as T
    }
}