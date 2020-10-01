package com.example.bookkaro.HouseholdHelper.ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookkaro.R;

public class HouseholdServiceViewHolder extends RecyclerView.ViewHolder {

    public TextView serviceName;
    public TextView servicePrice;
    public Button addItem;
    public ElegantNumberButton quantityChange;

    public HouseholdServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        serviceName = itemView.findViewById(R.id.item_name);
        servicePrice = itemView.findViewById(R.id.item_price);
        addItem = itemView.findViewById(R.id.add);
        quantityChange = itemView.findViewById(R.id.quantity);
    }
}
