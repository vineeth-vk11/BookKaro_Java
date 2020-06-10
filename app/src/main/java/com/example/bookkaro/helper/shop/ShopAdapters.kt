package com.example.bookkaro.helper.shop

import android.app.Application
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

class ShopItemAdapter(private val items: List<ShopItem>, private val application: Application, private val context: Context,
                      private val listener: (ShopItem, Int) -> Unit) : RecyclerView.Adapter<ShopItemViewHolder>() {

    private lateinit var shopUtils: ShopUtils
    private lateinit var storedCart: List<CartItem>

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        shopUtils = ShopUtils(application)
        storedCart = shopUtils.fetchQuantityItems()
        return ShopItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_shop_item, parent, false))
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = items[position]
        val priceText = "${application.getString(R.string.rupee_symbol)}${item.price}"
        Picasso.get().load(item.iconUrl).fit().into(holder.imageIcon)
        holder.textName.text = item.name
        holder.textPrice.text = priceText
        holder.textDesc.text = item.description

        storedCart.forEach {
            if (it.shopDocId == item.shopDocId && it.shopItemId == item.docId) {
                holder.quantityIndicatorNumber.text = "${it.quantity}"
                holder.quantityIndicatorNegative.visibility = View.VISIBLE
            }
        }

        holder.quantityIndicatorPositive.setOnClickListener {
            if (holder.quantityIndicatorNumber.text == application.resources.getString(R.string.quantity_add)) {
                listener(item, 1)
                holder.quantityIndicatorNumber.text = "1"
                holder.quantityIndicatorNegative.visibility = View.VISIBLE
            } else {
                listener(item, holder.quantityIndicatorNumber.text.toString().toInt() + 1)
                holder.quantityIndicatorNumber.text = "${holder.quantityIndicatorNumber.text.toString().toInt() + 1}"

            }

        }

        holder.quantityIndicatorNegative.setOnClickListener {
            if(holder.quantityIndicatorNumber.text == "1"){
                listener(item,0)
                holder.quantityIndicatorNumber.text = application.resources.getString(R.string.quantity_add)
                holder.quantityIndicatorNegative.visibility = View.GONE
            }else{
                listener(item,holder.quantityIndicatorNumber.text.toString().toInt() - 1)
                holder.quantityIndicatorNumber.text = "${holder.quantityIndicatorNumber.text.toString().toInt() - 1}"
            }
        }
    }

}