package com.example.bookkaro.ShopHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Models.ShopOrderItemModel;
import com.example.bookkaro.ShopHelper.ViewHolders.ShopOrderDetailsViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopOrderDetailsAdapter extends RecyclerView.Adapter<ShopOrderDetailsViewHolder> {

    Context context;
    ArrayList<ShopOrderItemModel> shopOrderItemModelArrayList;

    public ShopOrderDetailsAdapter(Context context, ArrayList<ShopOrderItemModel> shopOrderItemModelArrayList) {
        this.context = context;
        this.shopOrderItemModelArrayList = shopOrderItemModelArrayList;
    }

    @NonNull
    @Override
    public ShopOrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_shop_details_item,parent, false);
        return new ShopOrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOrderDetailsViewHolder holder, int position) {
        holder.itemName.setText(shopOrderItemModelArrayList.get(position).getItemName());
        holder.itemDesc.setText(shopOrderItemModelArrayList.get(position).getItemDesc());
        holder.itemPrice.setText(shopOrderItemModelArrayList.get(position).getItemPrice());
        Picasso.get().load(shopOrderItemModelArrayList.get(position).getItemIcon()).into(holder.itemIcon);
        holder.quantity.setText(String.valueOf(shopOrderItemModelArrayList.get(position).getItemQuantity()));

    }

    @Override
    public int getItemCount() {
        return shopOrderItemModelArrayList.size();
    }
}
