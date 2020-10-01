package com.example.bookkaro.ShopHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Models.ShopCategoryModel;
import com.example.bookkaro.ShopHelper.Models.ShopItemModel;
import com.example.bookkaro.ShopHelper.ViewHolders.ShopCategoryViewHolder;

import java.util.ArrayList;

public class ShopCategoryAdapter extends RecyclerView.Adapter<ShopCategoryViewHolder> {

    Context context;
    ArrayList<ShopCategoryModel> shopCategoryModelArrayList;
    ArrayList<ShopItemModel> shopItemModelArrayList;
    String shopNumber;

    public ShopCategoryAdapter(Context context, ArrayList<ShopCategoryModel> shopCategoryModelArrayList, String shopNumber) {
        this.context = context;
        this.shopCategoryModelArrayList = shopCategoryModelArrayList;
        this.shopNumber = shopNumber;
    }

    @NonNull
    @Override
    public ShopCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_shop_categories, parent, false);
        return new ShopCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCategoryViewHolder holder, int position) {
        holder.categoryName.setText(shopCategoryModelArrayList.get(position).getCategoryName());

        shopItemModelArrayList = new ArrayList<>();
        shopItemModelArrayList = shopCategoryModelArrayList.get(position).getShopItemModelArrayList();
        ShopCategoryItemAdapter shopCategoryItemAdapter = new ShopCategoryItemAdapter(context,shopItemModelArrayList,shopNumber);
        holder.categoryItemRecycler.setHasFixedSize(true);
        holder.categoryItemRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.categoryItemRecycler.setNestedScrollingEnabled(false);
        holder.categoryItemRecycler.setAdapter(shopCategoryItemAdapter);
    }

    @Override
    public int getItemCount() {
        return shopCategoryModelArrayList.size();
    }
}
