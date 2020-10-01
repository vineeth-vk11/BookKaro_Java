package com.example.bookkaro.DeliveryHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookkaro.R;

public class DeliveryItemViewHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public ElegantNumberButton itemQuantity;

    public DeliveryItemViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_name);
        itemQuantity = itemView.findViewById(R.id.quantity);

    }
}
