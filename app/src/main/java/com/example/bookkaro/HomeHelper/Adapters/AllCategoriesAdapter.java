package com.example.bookkaro.HomeHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.HomeHelper.Models.AllCategoriesModel;
import com.example.bookkaro.HomeHelper.ViewHolders.AllCategoriesViewHolder;
import com.example.bookkaro.R;

import java.util.ArrayList;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesViewHolder> {

    Context context;
    ArrayList<AllCategoriesModel> allCategoriesModelArrayList;
    String pincode;

    public AllCategoriesAdapter(Context context, ArrayList<AllCategoriesModel> allCategoriesModelArrayList, String pincode) {
        this.context = context;
        this.allCategoriesModelArrayList = allCategoriesModelArrayList;
        this.pincode = pincode;
    }

    @NonNull
    @Override
    public AllCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_categories_all,parent, false);
        return new AllCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesViewHolder holder, int position) {
        holder.categoryName.setText(allCategoriesModelArrayList.get(position).getCategoryName());

        ServiceAdapter serviceAdapter = new ServiceAdapter(context,allCategoriesModelArrayList.get(position).getServiceModelArrayList(),pincode);
        holder.servicesRecycler.setHasFixedSize(true);
        holder.servicesRecycler.setNestedScrollingEnabled(false);
        holder.servicesRecycler.setLayoutManager(new GridLayoutManager(context,3));
        holder.servicesRecycler.setAdapter(serviceAdapter);

    }

    @Override
    public int getItemCount() {
        return allCategoriesModelArrayList.size();
    }
}
