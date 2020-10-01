package com.example.bookkaro.ShopHelper.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookkaro.R;

public class ShopOrderDetailsViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemPrice;
    public TextView itemDesc;
    public ImageView itemIcon;
    public TextView quantity;

    public ShopOrderDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_name_text);
        itemPrice = itemView.findViewById(R.id.item_price_text);
        itemDesc = itemView.findViewById(R.id.item_desc_text);
        itemIcon = itemView.findViewById(R.id.item_image);
        quantity = itemView.findViewById(R.id.quantity);
    }
}
