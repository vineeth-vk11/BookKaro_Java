package com.example.bookkaro.helper.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import kotlinx.android.synthetic.main.order_item.view.*

class listItemsAdapter(private val list: MutableList<Order>) : RecyclerView.Adapter<listItemsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.item1.text = list[position].option

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val item1:TextView = itemView.order_list_item1

    }

}