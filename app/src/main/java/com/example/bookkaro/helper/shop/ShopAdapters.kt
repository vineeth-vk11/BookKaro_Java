package com.example.bookkaro.helper.shop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkaro.R
import com.example.bookkaro.mainui.makebooking.shops.SelectShopFragmentDirections
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_shop.view.*
import kotlinx.android.synthetic.main.list_item_shop_item.view.*

class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textName: TextView = view.shop_name_text
    val textAddress: TextView = view.shop_address_text
    val imageIcon: ImageView = view.shop_icon_image
}

class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textName: TextView = view.item_name_text
    val textPrice: TextView = view.item_price_text
    val textDesc: TextView = view.item_desc_text
    val imageIcon: ImageView = view.item_image
    val quantityIndicatorPositive: ImageView = view.quantity_indicator_postitive
    val quantityIndicatorNegative: ImageView = view.quantity_indicator_negative
    val quantityIndicatorNumber: TextView = view.quantity_indicator_number

}

class ShopAdapter(private val items: List<Shop>, private val context: Context, private val navController: NavController) : RecyclerView.Adapter<ShopViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        return ShopViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_shop, parent, false))
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shop = items[position]
        Picasso.get().load(shop.iconUrl).fit().into(holder.imageIcon)
        holder.textName.text = shop.name
        holder.textAddress.text = shop.address
        holder.itemView.setOnClickListener {
            val action = SelectShopFragmentDirections.actionBookShopServiceFragmentToSelectItemsFragment(shop.docId)
            navController.navigate(action)
        }
    }

}

class ShopItemAdapter(private val items: List<Map<String, ShopItem>>, private val context: Context) : RecyclerView.Adapter<ShopItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return ShopItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_shop_item, parent, false))
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = items[position].values.elementAt(0)
        val priceText = "${context.getString(R.string.rupee_symbol)}${item.price}"
        Picasso.get().load(item.iconUrl).fit().into(holder.imageIcon)
        holder.textName.text = item.name
        holder.textPrice.text = priceText
        holder.textDesc.text = item.description

        holder.quantityIndicatorPositive.setOnClickListener {
            if(holder.quantityIndicatorNumber.text == context.resources.getString(R.string.quantity_add)){
                holder.quantityIndicatorNumber.text = "1"
                holder.quantityIndicatorNegative.visibility = View.VISIBLE
            }else{
                //TODO:Update the Quantity over here (Increment)
                holder.quantityIndicatorNumber.text = "${holder.quantityIndicatorNumber.text.toString().toInt() + 1}"

            }
        }

        holder.quantityIndicatorNegative.setOnClickListener {
            if(holder.quantityIndicatorNumber.text == "1"){
                //TODO: Deselect the item i.e add code to make quantity 0 here.
                holder.quantityIndicatorNumber.text = context.resources.getString(R.string.quantity_add)
                holder.quantityIndicatorNegative.visibility = View.GONE
            }else{
                //TODO:Decrement the quantity over here
                holder.quantityIndicatorNumber.text = "${holder.quantityIndicatorNumber.text.toString().toInt() - 1}"
            }
        }
    }

}