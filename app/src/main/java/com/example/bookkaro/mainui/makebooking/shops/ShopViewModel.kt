package com.example.bookkaro.mainui.makebooking.shops

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.helper.ServicesGroup
import com.example.bookkaro.helper.shop.CartItem
import com.example.bookkaro.helper.shop.Shop
import com.example.bookkaro.helper.shop.ShopItem
import com.example.bookkaro.helper.shop.ShopUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ShopsRepository(private val application: Application) {

    private var firestoreDb = FirebaseFirestore.getInstance()

    fun getShops(): CollectionReference {
        return firestoreDb.collection(application.getString(R.string.firestore_collection_shop_data))
    }

    fun getShopItems(shopId: String): CollectionReference {
        return firestoreDb.collection("${application.getString(R.string.firestore_collection_shop_data)}/$shopId/${application.getString(R.string.firestore_collection_shop_data_subcollection_items)}")
    }

    fun getOrders(): CollectionReference {
        return firestoreDb.collection("OrderData")
    }

    fun getOrder(docId: String): CollectionReference {
        return firestoreDb.collection("OrderData/$docId/Items")
    }

}

class ShopViewModel(private val application: Application) : ViewModel() {

    private val TAG = "SHOPS_VIEW_MODEL"

    private val firestoreRepository = ShopsRepository(application)

    private var shops: MutableLiveData<List<Shop>> = MutableLiveData()
    private var shopItems: MutableLiveData<List<ShopItem>> = MutableLiveData()
    var cartItems: List<CartItem> = ShopUtils(application).fetchQuantityItems()

    val pin = 411014

    fun getShops(shopType: Long): LiveData<List<Shop>> {
        firestoreRepository.getShops().whereArrayContains(application.getString(R.string.firestore_collection_shop_data_field_service_location), pin).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
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
            shops.value = shopsList.filter { it.type == shopType }.toMutableList()
        }
        return shops
    }

    fun getShopItems(shopId: String): LiveData<List<ShopItem>> {
        firestoreRepository.getShopItems(shopId).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                Log.e(TAG, "Firestore shop items listening failed.")
                shops.value = null
                return@addSnapshotListener
            }

            val itemsList: MutableList<ShopItem> = mutableListOf()
            for (doc in querySnapshot!!) {
                val category = doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_category))!!
                itemsList.add(ShopItem(shopId,
                        doc.id,
                        doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_name))!!,
                        doc.getLong(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_price))!!,
                        doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_icon_url))!!,
                        doc.getString(application.getString(R.string.firestore_collection_shop_data_subcollection_items_field_item_description))!!,
                        category)
                )
            }
            itemsList.sortBy { it.category }
            shopItems.value = itemsList
        }
        return shopItems
    }

    private val _orderPlaced: MutableLiveData<Pair<Boolean, String?>> = MutableLiveData(Pair(false, null))
    val orderPlaced: LiveData<Pair<Boolean, String?>>
        get() = _orderPlaced

    fun placeOrder() {
        var price = 0L
        val items: MutableList<String> = mutableListOf()
        cartItems.forEachIndexed { index, cartItem ->
            items.add(cartItem.shopItemId)
            price += cartItem.itemPrice * cartItem.quantity
        }
        firestoreRepository.getOrders().add(mapOf(
                "userId" to FirebaseAuth.getInstance().uid,
                "status" to 100L,
                "shopName" to "",
                "shopIconUrl" to "",
                "shopAddress" to "",
                "serviceDate" to Date(),
                "serviceType" to ServicesGroup.SHOP_SERVICE,
                "shopNumber" to cartItems[0].shopDocId,
                "items" to items,
                "servicePrice" to price,
                "serviceName" to "Shop delivery")
        ).addOnSuccessListener { _orderPlaced.value = Pair(true, it.id) }
    }

    fun getSubtotal(): String {
        var price = 0L
        cartItems.forEach { item ->
            price += item.itemPrice * item.quantity
        }
        return "₹$price"
    }

    fun getDiscount() = "- ₹0"
    fun getExtraCharges() = "₹0"

    fun getTotal() = getSubtotal()

}

class ShopViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShopViewModel(application) as T
    }
}