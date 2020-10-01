package com.example.bookkaro.HouseholdHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class HouseholdOrderDetailsViewHolder extends RecyclerView.ViewHolder {

    public TextView serviceName;
    public TextView servicePrice;
    public TextView serviceQuantity;

    public HouseholdOrderDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        serviceName = itemView.findViewById(R.id.item_name);
        servicePrice = itemView.findViewById(R.id.item_price);
        serviceQuantity = itemView.findViewById(R.id.service_quantity_text);


    }
}
