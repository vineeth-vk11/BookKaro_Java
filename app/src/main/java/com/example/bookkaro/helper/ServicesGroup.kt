package com.example.bookkaro.helper

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class ServicesData(
        val docId: String,
        val serviceType: Long,
        val name: String,
        var image: String
) : Serializable

data class ServicesGroup(
        val headerTitle: String,
        val listItem: List<ServicesData>
) {
    companion object {
        const val DELIVERY_SERVICE = 100L
        const val HOUSEHOLD_SERVICE = 101L
        const val SHOP_SERVICE = 102L
    }
}