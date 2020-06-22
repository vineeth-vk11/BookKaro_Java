package com.example.bookkaro.helper

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class OrderPackages(var category:String,
                         var storeAddress: String,
                         var deliveryAddress: String,
                         var items:String,
                         var estimatedTotal:String,
                         var instructions: String):Serializable

data class SendPackages(var pickupAddress: String,
                        var deliveryAddress: String,
                        var packageContents: String,
                        var instructions: String):Serializable
