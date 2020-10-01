package com.example.bookkaro.HouseholdHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.HouseholdHelper.Models.HouseholdCartModel;
import com.example.bookkaro.HouseholdHelper.ViewHolders.HouseholdOrderDetailsViewHolder;
import com.example.bookkaro.R;

import java.util.ArrayList;

public class HouseholdOrderDetailsAdapter extends RecyclerView.Adapter<HouseholdOrderDetailsViewHolder> {

    Context context;
    ArrayList<HouseholdCartModel> householdCartModelArrayList;

    public HouseholdOrderDetailsAdapter(Context context, ArrayList<HouseholdCartModel> householdCartModelArrayList) {
        this.context = context;
        this.householdCartModelArrayList = householdCartModelArrayList;
    }

    @NonNull
    @Override
    public HouseholdOrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_household_service_order_details,parent,false);
        return new HouseholdOrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseholdOrderDetailsViewHolder holder, int position) {
        holder.serviceName.setText(householdCartModelArrayList.get(position).getServiceName());
        holder.servicePrice.setText(Integer.toString(householdCartModelArrayList.get(position).getServicePrice()));
        holder.serviceQuantity.setText(Integer.toString(householdCartModelArrayList.get(position).getServiceQuantity()));
    }

    @Override
    public int getItemCount() {
        return householdCartModelArrayList.size();
    }
}
