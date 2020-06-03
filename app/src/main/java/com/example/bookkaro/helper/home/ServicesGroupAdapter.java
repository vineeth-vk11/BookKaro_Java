package com.example.bookkaro.helper.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.example.bookkaro.helper.ServicesData;
import com.example.bookkaro.helper.ServicesGroup;

import java.util.List;

public class ServicesGroupAdapter extends RecyclerView.Adapter<ServicesGroupAdapter.ServicesGroupHolder> {

    private Context context;
    private List<ServicesGroup> servicesList;
    private NavController navController;

    public ServicesGroupAdapter(Context context, List<ServicesGroup> servicesList, NavController navController) {
        this.context = context;
        this.servicesList = servicesList;
        this.navController = navController;
    }

    @NonNull
    @Override
    public ServicesGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.services_item,parent,false);
        return new ServicesGroupHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesGroupHolder holder, int position) {
        holder.service_name.setText(servicesList.get(position).getHeaderTitle());
        List<ServicesData> servicesData = servicesList.get(position).getListItem();
        ServicesItemAdapter servicesItemAdapter = new ServicesItemAdapter(context, servicesData, navController);
        holder.category_list.setHasFixedSize(true);
        holder.category_list.setLayoutManager(new GridLayoutManager(context,3));
        holder.category_list.setAdapter(servicesItemAdapter);
    }

    @Override
    public int getItemCount() {
        return (servicesList != null ? servicesList.size(): 0);
    }

    public class ServicesGroupHolder extends RecyclerView.ViewHolder{
        TextView service_name;
        RecyclerView category_list;
        public ServicesGroupHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.categoryName);
            category_list = itemView.findViewById(R.id.CategoryList);
        }
    }
}
