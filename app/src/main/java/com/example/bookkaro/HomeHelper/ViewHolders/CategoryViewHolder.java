package com.example.bookkaro.HomeHelper.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryName;
    public ImageView categoryIcon;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryIcon = itemView.findViewById(R.id.iconHolder);
        categoryName = itemView.findViewById(R.id.nameHolder);

    }
}
