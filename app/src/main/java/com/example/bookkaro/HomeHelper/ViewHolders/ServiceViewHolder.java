package com.example.bookkaro.HomeHelper.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class ServiceViewHolder extends RecyclerView.ViewHolder {

    public TextView serviceName;
    public ImageView serviceIcon;
    public CardView serviceCard;
    public TextView availability;

    public ServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        serviceIcon = itemView.findViewById(R.id.iconHolder);
        serviceName = itemView.findViewById(R.id.nameHolder);
        serviceCard = itemView.findViewById(R.id.cardItem);
        availability = itemView.findViewById(R.id.not_available);
    }
}
