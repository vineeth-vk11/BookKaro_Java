package com.example.bookkaro.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_booking.view.*
import java.text.SimpleDateFormat
import java.util.*

data class Booking(val docID: String, val shopThumbnailUrl: String?, val shopName: String, val bookingDate: Date, val shopAddress: String, val servicePrice: Long, val serviceName: String, val serviceStatus: Long) {
    companion object {
        const val STATUS_RECEIVED = 100L
        const val STATUS_CONFIRMING_WITH_SHOP = 101L
        const val STATUS_CONFIRMED = 102L
        const val STATUS_CANCELED = 103L
        const val STATUS_COMPLETED = 104L

    }
}

class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val iconImage: ImageView = view.booking_shop_image
    val shopNameText: TextView = view.booking_shop_name
    val shopAddressText: TextView = view.booking_shop_address
    val serviceDateText: TextView = view.booking_date
    val serviceNameText: TextView = view.booking_service_name
    val servicePriceText: TextView = view.booking_price
    val helpText: TextView = view.booking_help
    val cancelText: TextView = view.booking_cancel
    val rescheduleText: TextView = view.booking_reschedule
    val statusText: TextView = view.booking_status
}

class BookingsAdapter(val items: List<Booking>, val context: Context) : RecyclerView.Adapter<BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        return BookingViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_booking, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private val formatter = SimpleDateFormat("MMMM dd hh:mm a", Locale.getDefault())

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = items[position]

        val priceText = "${context.getString(R.string.rupee_symbol)}${booking.servicePrice}"

        Picasso.get().load(booking.shopThumbnailUrl).fit().into(holder.iconImage)
        holder.shopNameText.text = booking.shopName
        holder.shopAddressText.text = booking.shopAddress
        holder.serviceDateText.text = formatter.format(booking.bookingDate)
        holder.serviceNameText.text = booking.serviceName
        holder.servicePriceText.text = priceText
        holder.statusText.text = when (booking.serviceStatus) {
            Booking.STATUS_RECEIVED -> context.getString(R.string.status_received)
            Booking.STATUS_CONFIRMING_WITH_SHOP -> context.getString(R.string.status_confirming)
            Booking.STATUS_CONFIRMED -> context.getString(R.string.status_confirmed)
            Booking.STATUS_CANCELED -> context.getString(R.string.status_canceled)
            Booking.STATUS_COMPLETED -> context.getString(R.string.status_completed)
            else -> context.getString(R.string.status_received)
        }
        //TODO: Set onClickListeners on helpText, cancelText, and rescheduleText
    }

}
