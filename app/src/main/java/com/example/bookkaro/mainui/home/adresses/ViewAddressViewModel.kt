package com.example.bookkaro.mainui.home.adresses

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookkaro.helper.home.Address
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class ViewAddressViewModel : ViewModel() {

    private val TAG = "ADDRESSES_VIEW_MODEL"

    private var addresses: MutableLiveData<List<Address>> = MutableLiveData(listOf())
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "nouid"

    fun getAddresses(): LiveData<List<Address>> {
        FirebaseFirestore.getInstance().collection("UserData/$uid/Addresses").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore listening failed.")
                addresses.value = null
                return@addSnapshotListener
            }
            val addressList = mutableListOf<Address>()
            for (doc in querySnapshot!!) {
                addressList.add(Address(
                        doc.id,
                        doc.getLong("type") ?: 0L,
                        doc.getString("displayText") ?: "",
                        doc.getLong("pincode") ?: 0L,
                        doc.getGeoPoint("location") ?: GeoPoint(0.0, 0.0),
                        doc.getBoolean("default") ?: false
                ))
            }
            addresses.value = addressList
        }
        return addresses
    }


    fun setDefaultAddress(docIdToMakeDefault: String) {
        addresses.value?.forEach { address ->
            if (address.docId == docIdToMakeDefault) {
                FirebaseFirestore.getInstance()
                        .collection("UserData/$uid/Addresses")
                        .document(address.docId)
                        .update("default", true)
            } else {
                FirebaseFirestore.getInstance()
                        .collection("UserData/$uid/Addresses")
                        .document(address.docId)
                        .update("default", false)
            }
        }
    }
}