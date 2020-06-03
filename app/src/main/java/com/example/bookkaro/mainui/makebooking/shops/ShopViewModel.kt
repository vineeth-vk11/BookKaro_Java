package com.example.bookkaro.mainui.makebooking.shops

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.helper.shop.Shop
import com.example.bookkaro.helper.shop.ShopItem
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ShopsRepository(private val application: Application) {

    private var firestoreDb = FirebaseFirestore.getInstance()

    fun getShops(): CollectionReference {
        return firestoreDb.collection(application.getString(R.string.firestore_collection_shop_data))
    }

    fun getShopItems(shopId: String): CollectionReference {
        return firestoreDb.collection("${application.getString(R.string.firestore_collection_shop_data)}/$shopId/${application.getString(R.string.firestore_collection_shop_data_subcollection_items)}")
    }

}

class ShopViewModel(private val application: Application) : ViewModel() {

    private val TAG = "SHOPS_VIEW_MODEL"

    private val firestoreRepository = ShopsRepository(application)

    private var shops: MutableLiveData<List<Shop>> = MutableLiveData()
    private var shopItems: MutableLiveData<List<Map<String, ShopItem>>> = MutableLiveData()

    fun getShops(shopType: Long): LiveData<List<Shop>> {
        firestoreRepository.getShops().whereEqualTo(application.getString(R.string.firestore_collection_shop_data_field_shop_type), shopType).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore shops listening failed.")
                shops.value = null
                return@addSnapshotListener
            }
            val shopsList: MutableList<Shop> = mutableListOf()
            for (doc in querySnapshot!!) {
                shopsList.add(Shop(
                        doc.id,
                        doc.getString(application.getString(R.string.firestore_collection_shop_data_field_shop_name))!!,
                        doc.getLong(application.getString(R.string.firestore_collection_shop_data_field_shop_type))!!,
                        doc.getString(application.getString(R.string.firestore_collection_shop_data_field_shop_icon_url))!!,
                        doc.getString(application.getString(R.string.firestore_collection_shop_data_field_shop_address))!!
                ))
            }
            shops.value = shopsList
        }
        return shops
    }

    fun getShopItems(shopId: String): LiveData<List<Map<String, ShopItem>>> {
        firestoreRepository.getShopItems(shopId).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore shop items listening failed.")
                shops.value = null
                return@addSnapshotListener
            }

            val itemsList: MutableList<Map<String, ShopItem>> = mutableListOf()
            for (doc in querySnapshot!!) {
                val category = doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_category))!!
                itemsList.add(mapOf(
                        category to
                                ShopItem(doc.id,
                                        doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_name))!!,
                                        doc.getLong(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_price))!!,
                                        doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_icon_url))!!,
                                        doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_description))!!,
                                        category
                                )
                ))
            }
            shopItems.value = itemsList
        }
        return shopItems
    }

}

class ShopViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(application) as T
    }
}