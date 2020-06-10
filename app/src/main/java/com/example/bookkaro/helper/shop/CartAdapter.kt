package com.example.bookkaro.helper.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import kotlinx.android.synthetic.main.list_item_cart.view.*

class CartAdapter(private val cart: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cart[position]
        holder.itemName.text = item.itemName
        holder.price.text = "₹${item.itemPrice * item.quantity}"
        holder.quantity.text = "Quantity: ${item.quantity}"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.item_name_text
        val price: TextView = itemView.item_price_text
        val quantity: TextView = itemView.item_quantity_text
    }

}
