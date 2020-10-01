package com.example.bookkaro.AddressHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class SavedAddressViewHolder extends RecyclerView.ViewHolder {

    public ImageView addressTypeIcon;
    public TextView addressHead;
    public TextView address;
    public CardView cardView;

    public SavedAddressViewHolder(@NonNull View itemView) {
        super(itemView);

        addressTypeIcon = itemView.findViewById(R.id.address_icon);
        addressHead = itemView.findViewById(R.id.address_header_text);
        address = itemView.findViewById(R.id.address_subheader_text);
        cardView = itemView.findViewById(R.id.address_card);
    }
}
