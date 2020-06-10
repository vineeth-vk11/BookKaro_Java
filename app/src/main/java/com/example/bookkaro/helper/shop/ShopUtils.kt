package com.example.bookkaro.helper.shop

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShopUtils(private val application: Application) {

    private var sharedPreferences: SharedPreferences = application.getSharedPreferences("Data", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor

    init {
        editor = sharedPreferences.edit()
    }

    companion object {
        private const val SHARED_PREFS_QUANTITY = "quantity"
    }

    private fun toQuantityItemString(listCart: List<CartItem>): String? {
        if (listCart == null) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<CartItem>>() {}.type
        return gson.toJson(listCart, listType)
    }

    private fun toQuantityItemList(QuantityString: String?): List<CartItem> {
        if (QuantityString == null) {
            return emptyList<CartItem>()
        }
        val gson = Gson()
        val listType = object : TypeToken<List<CartItem>?>() {}.type
        return gson.fromJson<List<CartItem>>(QuantityString, listType)
    }

    private fun saveQuantity(quantityString: String) = editor.putString(SHARED_PREFS_QUANTITY, quantityString).commit()

    private fun fetchQuantityString(): String? = sharedPreferences.getString(SHARED_PREFS_QUANTITY, null)

    fun insertQuantityItems(listCart: List<CartItem>) {
        val quantityString = toQuantityItemString(listCart)
        saveQuantity(quantityString ?: "null")
    }

    fun fetchQuantityItems(): List<CartItem> {
        var quantityStrings = fetchQuantityString()
        return toQuantityItemList(quantityStrings)
    }
}