package com.example.bookkaro.ShopHelper.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class ShopViewHolder extends RecyclerView.ViewHolder {

    public TextView shopName;
    public ImageView shopIcon;
    public TextView shopAddress;
    public CardView shopItem;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);

        shopName = itemView.findViewById(R.id.shop_name_text);
        shopIcon = itemView.findViewById(R.id.shop_icon_image);
        shopAddress = itemView.findViewById(R.id.shop_address_text);
        shopItem = itemView.findViewById(R.id.shopItem);
    }
}
