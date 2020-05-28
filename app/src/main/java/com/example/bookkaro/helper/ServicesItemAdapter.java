package com.example.bookkaro.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.example.bookkaro.mainui.HomeFragmentDirections;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServicesItemAdapter extends RecyclerView.Adapter<ServicesItemAdapter.ServicesViewHolder> {

    private Context context;
    private List<ServicesData> itemDataList;
    private NavController navController;

    public ServicesItemAdapter(Context context, List<ServicesData> itemDataList, NavController navController) {
        this.context = context;
        this.itemDataList = itemDataList;
        this.navController = navController;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ServicesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        final ServicesData data = itemDataList.get(position);
        holder.service_name.setText(data.getName());
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.service_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getServiceType() == ServicesGroup.HOUSEHOLD_SERVICE) {
                    Log.i("ServicesItemAdapter", "Current destination is: " + navController.getCurrentDestination());
                    HomeFragmentDirections.ActionHomeFragmentToBookHouseholdServicesFragment action = HomeFragmentDirections.actionHomeFragmentToBookHouseholdServicesFragment(data);
                    navController.navigate(action);
                } //Add remaining services checks here with else if
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size():0);
    }

    class ServicesViewHolder extends RecyclerView.ViewHolder {
        TextView service_name;
        ImageView service_icon;

        ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            service_icon = itemView.findViewById(R.id.iconHolder);
            service_name = itemView.findViewById(R.id.nameHolder);
        }
    }
}
