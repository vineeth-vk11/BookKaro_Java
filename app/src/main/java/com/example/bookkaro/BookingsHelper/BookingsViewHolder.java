package com.example.bookkaro.BookingsHelper;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

import org.w3c.dom.Text;

public class BookingsViewHolder extends RecyclerView.ViewHolder {

    public TextView serviceName;
    public ImageView serviceIcon;
    public TextView dateTime;
    public CardView item;

    public BookingsViewHolder(@NonNull View itemView) {
        super(itemView);

        serviceName = itemView.findViewById(R.id.booking_shop_name);
        serviceIcon = itemView.findViewById(R.id.booking_shop_image);
        dateTime = itemView.findViewById(R.id.dateTime);
        item = itemView.findViewById(R.id.bookingItem);

    }
}
