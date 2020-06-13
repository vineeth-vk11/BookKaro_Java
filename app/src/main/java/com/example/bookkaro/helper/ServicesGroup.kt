package com.example.bookkaro.helper

import androidx.annotation.Keep
import com.example.bookkaro.R
import java.io.Serializable

@Keep
data class ServicesData(
        val docId: String,
        val serviceType: Long,
        val serviceCategory: Long,
        var image: String
) : Serializable

data class ServicesGroup(
        val headerTitle: String,
        val listItem: List<ServicesData>
) {
    companion object {
        //Service types:
        const val DELIVERY_SERVICE = 100L
        const val HOUSEHOLD_SERVICE = 101L
        const val SHOP_SERVICE = 102L

        //Service categories:
        const val DELIVERY_SERVICE_ORDER = 1000L
        const val DELIVERY_SERVICE_SEND_PACKAGES = 1001L
        const val HOUSEHOLD_SERVICE_PLUMBER = 1002L
        const val HOUSEHOLD_SERVICE_PEST_CONTROL = 1003L
        const val HOUSEHOLD_SERVICE_PAINTER = 1004L
        const val HOUSEHOLD_SERVICE_ELECTRICIAN = 1005L
        const val HOUSEHOLD_SERVICE_CARPENTER = 1006L
        const val SHOP_SERVICE_GENERAL_STORE = 1007L
        const val SHOP_SERVICE_BEAUTY_STORE = 1008L
        const val SHOP_SERVICE_CLOTHING_STORE = 1009L

        fun getNameStringId(typeOrCategory: Long): Int {
            return when (typeOrCategory) {
                DELIVERY_SERVICE -> R.string.service_delivery
                HOUSEHOLD_SERVICE -> R.string.service_household
                SHOP_SERVICE -> R.string.service_shop
                DELIVERY_SERVICE_ORDER -> R.string.service_delivery_order
                DELIVERY_SERVICE_SEND_PACKAGES -> R.string.service_delivery_send_packages
                HOUSEHOLD_SERVICE_PLUMBER -> R.string.service_household_plumber
                HOUSEHOLD_SERVICE_PEST_CONTROL -> R.string.service_household_pest_control
                HOUSEHOLD_SERVICE_PAINTER -> R.string.service_household_painter
                HOUSEHOLD_SERVICE_ELECTRICIAN -> R.string.service_household_electrician
                HOUSEHOLD_SERVICE_CARPENTER -> R.string.service_household_carpenter
                SHOP_SERVICE_GENERAL_STORE -> R.string.service_shop_general_store
                SHOP_SERVICE_BEAUTY_STORE -> R.string.service_shop_beauty_store
                SHOP_SERVICE_CLOTHING_STORE -> R.string.service_shop_clothing_store
                else -> R.string.invalid
            }
        }

    }
}