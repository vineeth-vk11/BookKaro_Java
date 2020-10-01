package com.example.bookkaro.HomeHelper.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;

public class AllCategoriesViewHolder extends RecyclerView.ViewHolder {

    public TextView categoryName;
    public RecyclerView servicesRecycler;

    public AllCategoriesViewHolder(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.categoryName);
        servicesRecycler = itemView.findViewById(R.id.categoryItemsRecycler);
    }
}
