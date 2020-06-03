package com.example.bookkaro.mainui.makebooking.householdservices

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.helper.bookings.Review
import com.example.bookkaro.helper.home.ServiceOrPackageToBook
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ServicesRepository(private val serviceDocumentId: String, private val application: Application) {

    private var firestoreDB = FirebaseFirestore.getInstance()

    fun getServices(): CollectionReference {
        return firestoreDB.collection("AppData/Services/Data/$serviceDocumentId/ServicesList")
    }

    fun getPackages(): CollectionReference {
        return firestoreDB.collection("AppData/Services/Data/$serviceDocumentId/PackagesList")
    }

    fun getReviews(): CollectionReference {
        return firestoreDB.collection("AppData/Services/Data/$serviceDocumentId/ReviewsList")
    }

}

class BookHouseholdServicesViewModel(serviceDocumentId: String, application: Application) : ViewModel() {

    private val TAG = "BOOK_HOUSEHO_VIEW_MODEL"

    private val firestoreRepository = ServicesRepository(serviceDocumentId, application)

    private var services: MutableLiveData<List<ServiceOrPackageToBook>> = MutableLiveData()
    private var packages: MutableLiveData<List<ServiceOrPackageToBook>> = MutableLiveData()
    private var reviews: MutableLiveData<List<Review>> = MutableLiveData()

    fun getServices(): LiveData<List<ServiceOrPackageToBook>> {
        firestoreRepository.getServices().addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore Categories listening failed.")
                services.value = null
                return@addSnapshotListener
            }

            val servicesList: MutableList<ServiceOrPackageToBook> = mutableListOf()
            for (doc in querySnapshot!!) {
                servicesList.add(ServiceOrPackageToBook(
                        doc.getString("name")!!,
                        doc.getLong("price")!!
                ))
            }
            services.value = servicesList
        }
        return services
    }

    fun getPackages(): LiveData<List<ServiceOrPackageToBook>> {
        firestoreRepository.getPackages().addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore Categories listening failed.")
                services.value = null
                return@addSnapshotListener
            }

            val packagesList: MutableList<ServiceOrPackageToBook> = mutableListOf()
            for (doc in querySnapshot!!) {
                packagesList.add(ServiceOrPackageToBook(
                        doc.getString("name")!!,
                        doc.getLong("price")!!
                ))
            }
            packages.value = packagesList
        }
        return packages
    }

    fun getReviews(): LiveData<List<Review>> {
        firestoreRepository.getReviews().addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore Categories listening failed.")
                services.value = null
                return@addSnapshotListener
            }

            val reviewsList: MutableList<Review> = mutableListOf()
            for (doc in querySnapshot!!) {
                reviewsList.add(Review(
                        doc.getString("reviewerName")!!,
                        doc.getString("reviewerIcon"),
                        doc.getString("reviewContent"),
                        doc.getLong("reviewRating")!!
                ))
            }
            reviews.value = reviewsList
        }
        return reviews
    }

}

class BookHouseholdServicesViewModelFactory(private val serviceDocumentId: String, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookHouseholdServicesViewModel(serviceDocumentId, application) as T
    }
}