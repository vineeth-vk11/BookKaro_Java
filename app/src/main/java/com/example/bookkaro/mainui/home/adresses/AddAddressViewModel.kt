package com.example.bookkaro.mainui.home.adresses

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class AddAddressViewModel : ViewModel() {

    fun saveAddressToFirestore(addressLine1: String, landmark: String, location: GeoPoint, pincode: String, type: Long) {
        FirebaseFirestore.getInstance().collection("UserData/${FirebaseAuth.getInstance().uid}/Addresses").add(
                mapOf(
                        "displayText" to addressLine1,
                        "landmark" to landmark,
                        "location" to location,
                        "pincode" to pincode.toLong(),
                        "type" to type
                )
        )
    }

}