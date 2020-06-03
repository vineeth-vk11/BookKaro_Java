package com.example.bookkaro.helper.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}

class ServiceOrPackageToBookAdapter(private val items: List<ServiceOrPackageToBook>, private val context: Context) : RecyclerView.Adapter<ServiceOrPackageToBookViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceOrPackageToBookViewHolder {
        return ServiceOrPackageToBookViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_service_or_package, parent, false))
    }

    override fun onBindViewHolder(holder: ServiceOrPackageToBookViewHolder, position: Int) {
        holder.textName.text = items[position].name
        val price = "${context.getString(R.string.rupee_symbol)}${items[position].price}"
        holder.textPrice.text = price
    }

}
