package com.example.bookkaro.helper.shop

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShopUtils(
        private val context: Context
) {
    private var sharedPreferences: SharedPreferences =
            context.getSharedPreferences("Data", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor
    init {
        editor = sharedPreferences.edit()
    }
    companion object {
        private val SHARED_PREFS_QUANTITY = "quantity"
    }
    private fun toQuantityItemString(QuantityList: List<ItemQuantity>): String? {
        if (QuantityList == null) {
            return null
        }
        val gson = Gson()
        val listType = object : TypeToken<List<ItemQuantity>>() {}.type
        return gson.toJson(QuantityList, listType)
    }
    private fun toQuantityItemList(QuantityString: String?): List<ItemQuantity> {
        if (QuantityString == null) {
            return emptyList<ItemQuantity>()
        }
        val gson = Gson()
        val listType = object : TypeToken<List<ItemQuantity>?>() {}.type
        return gson.fromJson<List<ItemQuantity>>(QuantityString, listType)
    }
    private fun saveQuantity(quantityString: String) = editor.putString(SHARED_PREFS_QUANTITY, quantityString).commit()
    private fun fetchQuantityString(): String? = sharedPreferences.getString(SHARED_PREFS_QUANTITY, null)

    fun insertQuantityItems(QuantityList: List<ItemQuantity>) {
        val quantityString = toQuantityItemString(QuantityList)
        saveQuantity(quantityString ?: "null")
    }
    fun fetchQuantityItems(): List<ItemQuantity> {
        var quantityStrings = fetchQuantityString()
        return toQuantityItemList(quantityStrings)
    }
}