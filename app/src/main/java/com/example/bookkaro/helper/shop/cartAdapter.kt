package com.example.bookkaro.helper.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import kotlinx.android.synthetic.main.list_item_cart.view.*
import kotlinx.android.synthetic.main.order_item.view.*

class cartAdapter(private val cart: MutableList<Cart>) : RecyclerView.Adapter<cartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemname.text = cart[position].itemName
        holder.itemname.text = cart[position].price

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemname: TextView = itemView.cart_item_name_text
        val price:TextView = itemView.cart_item_price

    }

}
