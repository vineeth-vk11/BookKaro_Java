package com.example.bookkaro.helper.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import kotlinx.android.synthetic.main.profile_option_list_item.view.*

data class ProfileOption(val drawableResource: Int, val title: String, val clickListener: View.OnClickListener)

class ProfileOptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageIcon: ImageView = view.profile_option_icon
    val textTitle: TextView = view.profile_option_text
}

class ProfileOptionAdapter(private val items: ArrayList<ProfileOption>, val context: Context) : RecyclerView.Adapter<ProfileOptionViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOptionViewHolder {
        return ProfileOptionViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_option_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileOptionViewHolder, position: Int) {
        holder.imageIcon.setImageResource(items[position].drawableResource)
        holder.textTitle.text = items[position].title
        holder.itemView.setOnClickListener(items[position].clickListener)
    }

}

