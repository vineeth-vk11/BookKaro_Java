package com.example.bookkaro.DeliveryHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.DeliveryHelper.Models.DeliveryItemModel;
import com.example.bookkaro.DeliveryHelper.ViewHolders.DeliveryOrderDetailsViewHolder;
import com.example.bookkaro.R;

import java.util.ArrayList;

public class DeliveryOrderDetailsAdapter extends RecyclerView.Adapter<DeliveryOrderDetailsViewHolder> {

    Context context;
    ArrayList<DeliveryItemModel> deliveryItemModelArrayList;

    public DeliveryOrderDetailsAdapter(Context context, ArrayList<DeliveryItemModel> deliveryItemModelArrayList) {
        this.context = context;
        this.deliveryItemModelArrayList = deliveryItemModelArrayList;
    }

    @NonNull
    @Override
    public DeliveryOrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_delivery_order_details_item,parent,false);
        return new DeliveryOrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryOrderDetailsViewHolder holder, int position) {
        holder.itemName.setText(deliveryItemModelArrayList.get(position).getItemName());
        holder.itemQuantity.setText(deliveryItemModelArrayList.get(position).getItemQuantity());
    }

    @Override
    public int getItemCount() {
        return deliveryItemModelArrayList.size();
    }
}
