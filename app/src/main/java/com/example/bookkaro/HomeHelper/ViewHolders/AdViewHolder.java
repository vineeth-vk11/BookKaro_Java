package com.example.bookkaro.HomeHelper.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class AdViewHolder extends RecyclerView.ViewHolder {

    public ImageView adImage;

    public AdViewHolder(@NonNull View itemView) {
        super(itemView);

        adImage = itemView.findViewById(R.id.AdImage);
    }
}
