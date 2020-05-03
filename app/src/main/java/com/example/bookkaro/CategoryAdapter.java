package com.example.bookkaro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    Context context;
    ArrayList<Category> categories;

    public CategoryAdapter(Context c, ArrayList<Category> p){
        context = c;
        categories = p;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(context).inflate(R.layout.category_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.categoryName.setText(categories.get(position).getCategoryName());
        Picasso.get().load(categories.get(position).getCategoryIcon()).into(holder.categoryIcon);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryIcon;
        public CategoryHolder(View itemView){
            super(itemView);
            categoryName = itemView.findViewById(R.id.nameHolder);
            categoryIcon = itemView.findViewById(R.id.iconHolder);
        }
    }

}
