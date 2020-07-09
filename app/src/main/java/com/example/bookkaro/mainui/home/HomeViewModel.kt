package com.example.bookkaro.mainui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.helper.ServicesData
import com.example.bookkaro.helper.ServicesGroup
import com.example.bookkaro.helper.home.Address
import com.example.bookkaro.helper.home.Ads
import com.example.bookkaro.helper.home.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

class HomeRepository(private val application: Application) {

    private var firestoreDB = FirebaseFirestore.getInstance()

    fun getAds(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_app_data)).document(application.getString(R.string.firestore_collection_app_data_doc_ads)).collection(application.getString(R.string.firestore_collection_app_data_doc_ads_subcollection_data))
    }

    fun getCategories(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_app_data)).document(application.getString(R.string.firestore_collection_app_data_doc_categories)).collection(application.getString(R.string.firestore_collection_app_data_doc_categories_subcollection_data))
    }

    fun getServices(): CollectionReference {
        return firestoreDB.collection(application.getString(R.string.firestore_collection_app_data)).document(application.getString(R.string.firestore_collection_app_data_doc_services)).collection(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data))
    }

}

class HomeViewModel(private val application: Application) : ViewModel() {

    private val TAG = "HOME_VIEW_MODEL"

    private val firestoreRepository = HomeRepository(application)

    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "nouid"

    private val categories: MutableLiveData<List<Category>> = MutableLiveData()
    private val ads: MutableLiveData<List<Ads>> = MutableLiveData()
    private val services: MutableLiveData<List<ServicesGroup>> = MutableLiveData()

    private val _addresses: MutableLiveData<List<Address>> = MutableLiveData()
    fun getAddresses(): LiveData<List<Address>> {
        FirebaseFirestore.getInstance().collection("UserData/$uid/Addresses").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore listening failed.")
                _addresses.value = null
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
            _addresses.value = addressList
        }
        return _addresses
    }

    fun getCategories(): LiveData<List<Category>> {
        firestoreRepository.getCategories().orderBy(application.getString(R.string.firestore_collection_app_data_doc_categories_field_order_in_category)).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore Categories listening failed.")
                ads.value = null
                return@addSnapshotListener
            }

            val categoriesList: MutableList<Category> = mutableListOf()
            for (doc in querySnapshot!!) {
                categoriesList.add(Category(
                        doc.getString(application.getString(R.string.firestore_collection_app_data_doc_categories_field_name)),
                        doc.getString(application.getString(R.string.firestore_collection_app_data_doc_categories_field_icon))
                ))
            }
            categories.value = categoriesList
        }
        return categories
    }

    fun getAds(): LiveData<List<Ads>> {
        firestoreRepository.getAds().orderBy(application.getString(R.string.firestore_collection_app_data_doc_ads_subcollection_data_field_order_in_category)).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore Ads listening failed.")
                ads.value = null
                return@addSnapshotListener
            }

            val adsList: MutableList<Ads> = mutableListOf()
            for (doc in querySnapshot!!) {
                adsList.add(Ads(doc.getString(application.getString(R.string.firestore_collection_app_data_doc_ads_subcollection_data_field_image))))
            }
            ads.value = adsList
        }
        return ads
    }

    fun getServices(pin: Long): LiveData<List<ServicesGroup>> {
        firestoreRepository.getServices().whereArrayContains(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_locations), pin).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore services listening failed.")
                ads.value = null
                return@addSnapshotListener
            }

            //Currently, the only services are delivery, household, and shop. To add a service group, create another MutableList<ServicesData> for that service group
            val deliveryServices: MutableList<ServicesData> = mutableListOf()
            val householdServices: MutableList<ServicesData> = mutableListOf()
            val shopServices: MutableList<ServicesData> = mutableListOf()

            for (doc in querySnapshot!!) {
                //This will sort each respective service into its respective list. To add another service group, check the service type and add the service data into its respective list created ealier.
                when (doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_type))) {
                    ServicesGroup.DELIVERY_SERVICE -> deliveryServices.add(ServicesData(doc.id, doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_type))!!, doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_name))!!, doc.getString(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_icon_url))!!))
                    ServicesGroup.HOUSEHOLD_SERVICE -> householdServices.add(ServicesData(doc.id, doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_type))!!, doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_name))!!, doc.getString(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_icon_url))!!))
                    ServicesGroup.SHOP_SERVICE -> shopServices.add(ServicesData(doc.id, doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_type))!!, doc.getLong(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_service_name))!!, doc.getString(application.getString(R.string.firestore_collection_app_data_doc_services_subcollection_data_field_icon_url))!!))
                }
            }
            //Everything can now be added into a list of ServicesGroups. To add another service group, add that list to servicesList.
            val servicesList: MutableList<ServicesGroup> = mutableListOf()
            if (deliveryServices.isNotEmpty())
                servicesList.add(ServicesGroup(application.getString(R.string.service_delivery), deliveryServices))
            if (householdServices.isNotEmpty())
                servicesList.add(ServicesGroup(application.getString(R.string.service_household), householdServices))
            if (shopServices.isNotEmpty())
                servicesList.add(ServicesGroup(application.getString(R.string.service_shop), shopServices))
            services.value = servicesList
        }
        return services
    }

}

class HomeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(application) as T
    }
}