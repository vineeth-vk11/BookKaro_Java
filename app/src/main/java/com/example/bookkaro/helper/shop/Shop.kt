package com.example.bookkaro.helper.shop

data class Shop(
        val docId: String,
        val name: String,
        val type: Long,
        val iconUrl: String,
        val address: String
) {
    companion object {
        const val SHOP_TYPE_GENERAL = 100L
        const val SHOP_TYPE_BEAUTY = 101L
        const val SHOP_TYPE_CLOTHING = 102L
    }
}

data class ShopItem(
        val docId: String,
        val name: String,
        val price: Long,
        val iconUrl: String,
        val description: String,
        val category: String
)