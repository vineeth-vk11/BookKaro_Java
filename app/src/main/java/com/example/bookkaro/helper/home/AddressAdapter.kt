package com.example.bookkaro.helper.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import com.example.bookkaro.mainui.home.adresses.ViewAddressViewModel
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.list_item_address.view.*

class AddressAdapter(private val navController: NavController, private val viewModel: ViewAddressViewModel) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    var data = listOf<Address>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val item = data[position]
        when (item.type) {
            Address.ADDRESS_HOME -> {
                holder.addressHeaderText.setText(R.string.address_home)
                holder.addressIconImage.setImageResource(R.drawable.ic_address_home)
            }
            Address.ADDRESS_OFFICE -> {
                holder.addressHeaderText.setText(R.string.address_work)
                holder.addressIconImage.setImageResource(R.drawable.ic_address_office)
            }
            Address.ADDRESS_OTHER -> {
                holder.addressHeaderText.setText(R.string.address_other)
                holder.addressIconImage.setImageResource(R.drawable.ic_location_on_a_light_bg)
            }
        }
        holder.addressSubHeaderText.text = item.displayText

        holder.addressCard.setOnClickListener {
            viewModel.setDefaultAddress(item.docId)
            navController.navigate(R.id.action_viewAddressFragment_to_homeFragment)
        }
    }

    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addressIconImage: ImageView = itemView.address_icon
        val addressHeaderText: TextView = itemView.address_header_text
        val addressSubHeaderText: TextView = itemView.address_subheader_text
        val addressCard: MaterialCardView = itemView.address_card
    }

}