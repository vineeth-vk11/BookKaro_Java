package com.example.bookkaro.ShopHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class ShopCategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryName;
    public RecyclerView categoryItemRecycler;

    public ShopCategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.categoryName);
        categoryItemRecycler = itemView.findViewById(R.id.categoryItemsRecycler);
    }
}
