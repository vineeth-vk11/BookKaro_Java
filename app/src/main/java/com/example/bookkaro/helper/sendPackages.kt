package com.example.bookkaro.helper

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class OrderPackages(val category:String,
                         val storeAddress: String,
                         val deliveryAddress: String,
                         val items:String,
                         val estimatedTotal:String,
                         val instructions: String):Serializable

data class SendPackages(val pickupAddress: String,
                        val deliveryAddress: String,
                        val packageContents: String,
                        val instructions: String):Serializable
