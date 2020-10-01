package com.example.bookkaro.HomeHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.HomeHelper.Models.CategoryModel;
import com.example.bookkaro.HomeHelper.ViewHolders.CategoryViewHolder;
import com.example.bookkaro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;
    ArrayList<CategoryModel> categoryModelArrayList;

    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryName.setText(categoryModelArrayList.get(position).getCategoryName());
        Picasso.get().load(categoryModelArrayList.get(position).getCategoryIcon()).into(holder.categoryIcon);
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }
}
