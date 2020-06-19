package com.example.bookkaro.helper.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import kotlinx.android.synthetic.main.list_item_service_or_package.view.*


data class ServiceOrPackageToBook(
        val name: String,
        val price: Long
)

class ServiceOrPackageToBookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textName: TextView = view.service_name_text
    val textPrice: TextView = view.service_price_text
    val quantityIndicatorPositive: ImageView = view.quantity_indicator_postitive
    val quantityIndicatorNegative: ImageView = view.quantity_indicator_negative
    val quantityIndicatorNumber: TextView = view.quantity_indicator_number
}

class ServiceOrPackageToBookAdapter(private val items: List<ServiceOrPackageToBook>, private val context: Context, private val listener: (ServiceOrPackageToBook, Int) -> Unit) : RecyclerView.Adapter<ServiceOrPackageToBookViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceOrPackageToBookViewHolder {
        return ServiceOrPackageToBookViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_service_or_package, parent, false))
    }

    override fun onBindViewHolder(holder: ServiceOrPackageToBookViewHolder, position: Int) {
        val item = items[position]

        holder.textName.text = item.name
        val price = "${context.getString(R.string.rupee_symbol)}${item.price}"
        holder.textPrice.text = price

        holder.quantityIndicatorPositive.setOnClickListener {
            if (holder.quantityIndicatorNumber.text == context.getString(R.string.quantity_add)) {
                listener(item, 1)
                holder.quantityIndicatorNumber.text = "1"
                holder.quantityIndicatorNegative.visibility = View.VISIBLE
            } else {
                listener(item, holder.quantityIndicatorNumber.text.toString().toInt() + 1)
                holder.quantityIndicatorNumber.text = "${holder.quantityIndicatorNumber.text.toString().toInt() + 1}"
            }
        }

        holder.quantityIndicatorNegative.setOnClickListener {
            if (holder.quantityIndicatorNumber.text == "1") {
                listener(item, 0)
                holder.quantityIndicatorNumber.text = context.getString(R.string.quantity_add)
                holder.quantityIndicatorNegative.visibility = View.GONE
            } else {
                listener(item, holder.quantityIndicatorNumber.text.toString().toInt() - 1)
                holder.quantityIndicatorNumber.text = "${holder.quantityIndicatorNumber.text.toString().toInt() - 1}"
            }
        }

        holder.quantityIndicatorNegative.setOnLongClickListener {
            listener(item, 0)
            holder.quantityIndicatorNumber.text = context.getString(R.string.quantity_add)
            it.visibility = View.GONE
            true
        }
    }

}
