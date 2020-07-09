package com.example.bookkaro.helper.home

import androidx.annotation.Keep
import com.google.firebase.firestore.GeoPoint

@Keep
data class Address(
        val docId: String,
        val type: Long,
        val displayText: String,
        val pincode: Long,
        val location: GeoPoint,
        val default: Boolean
) {
    companion object {
        const val ADDRESS_HOME = 500L
        const val ADDRESS_OFFICE = 501L
        const val ADDRESS_OTHER = 502L
    }
}