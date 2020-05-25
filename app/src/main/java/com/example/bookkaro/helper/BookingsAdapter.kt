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

data class Booking(val docID: String,
                   val acceptedShopNumber: String?,
                   val serviceDate: Date,
                   val serviceName: String,
                   val servicePrice: Long,
                   val shopAddress: String?,
                   val shopIconUrl: String?,
                   val shopName: String?,
                   val status: Long,
                   val userId: String
) {
    companion object {
        const val STATUS_PENDING = 100L
        const val STATUS_ACCEPTED = 101L
        const val STATUS_STARTED = 102L
        const val STATUS_COMPLETED = 103L
        const val STATUS_CANCELED = 104L
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

class BookingsAdapter(private val items: List<Booking>, private val context: Context) : RecyclerView.Adapter<BookingViewHolder>() {

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

        if (booking.status == Booking.STATUS_PENDING) {
            holder.iconImage.setImageResource(R.drawable.outline_storefront_24)
            holder.shopNameText.text = context.getString(R.string.shop_acceptance_waiting)
            holder.shopAddressText.visibility = View.GONE
            holder.serviceDateText.text = formatter.format(booking.serviceDate)
            holder.serviceNameText.text = booking.serviceName
            holder.servicePriceText.text = priceText
            holder.statusText.text = context.getString(R.string.status_pending)
            holder.statusText.setTextColor(context.getColor(R.color.statusPending))
        } else {
            Picasso.get().load(booking.shopIconUrl).fit().into(holder.iconImage)
            holder.shopNameText.text = booking.shopName
            holder.shopAddressText.text = booking.shopAddress
            holder.serviceDateText.text = formatter.format(booking.serviceDate)
            holder.serviceNameText.text = booking.serviceName
            holder.servicePriceText.text = priceText
            when (booking.status) {
                Booking.STATUS_ACCEPTED -> {
                    holder.statusText.text = context.getString(R.string.status_accepted)
                    holder.statusText.setTextColor(context.getColor(R.color.statusAccepted))
                }
                Booking.STATUS_STARTED -> {
                    holder.statusText.text = context.getString(R.string.status_started)
                    holder.statusText.setTextColor(context.getColor(R.color.statusAccepted))
                }
                Booking.STATUS_COMPLETED -> {
                    holder.statusText.text = context.getString(R.string.status_completed)
                    holder.statusText.setTextColor(context.getColor(R.color.statusAccepted))
                }
                Booking.STATUS_CANCELED -> {
                    holder.statusText.text = context.getString(R.string.status_canceled)
                    holder.statusText.setTextColor(context.getColor(R.color.statusCanceled))
                }
            }
        }
        //TODO: Set onClickListeners on helpText, cancelText, and rescheduleText
    }

}
