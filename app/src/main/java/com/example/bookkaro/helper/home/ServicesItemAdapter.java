package com.example.bookkaro.helper.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.example.bookkaro.helper.ServicesData;
import com.example.bookkaro.helper.ServicesGroup;
import com.example.bookkaro.helper.shop.Shop;
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
        holder.service_name.setText(ServicesGroup.Companion.getNameStringId(data.getServiceCategory()));
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.service_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long serviceType = data.getServiceType();
                if (serviceType == ServicesGroup.HOUSEHOLD_SERVICE) {
                    HomeFragmentDirections.ActionHomeFragmentToBookHouseholdServicesFragment action = HomeFragmentDirections.actionHomeFragmentToBookHouseholdServicesFragment(data);
                    navController.navigate(action);
                } else if (serviceType == ServicesGroup.SHOP_SERVICE) {
                    long type = Shop.SHOP_TYPE_GENERAL;
                    switch ((int) data.getServiceCategory()) {
                        case (int) ServicesGroup.SHOP_SERVICE_GENERAL_STORE:
                            type = Shop.SHOP_TYPE_GENERAL;
                            break;
                        case (int) ServicesGroup.SHOP_SERVICE_BEAUTY_STORE:
                            type = Shop.SHOP_TYPE_BEAUTY;
                            break;
                        case (int) ServicesGroup.SHOP_SERVICE_CLOTHING_STORE:
                            type = Shop.SHOP_TYPE_CLOTHING;
                            break;
                    }
                    HomeFragmentDirections.ActionHomeFragmentToBookShopServiceFragment action = HomeFragmentDirections.actionHomeFragmentToBookShopServiceFragment(type);
                    navController.navigate(action);
                } else if (serviceType == ServicesGroup.DELIVERY_SERVICE) {
                    switch ((int) data.getServiceCategory()) {
                        case (int) ServicesGroup.DELIVERY_SERVICE_ORDER: {
                            HomeFragmentDirections.ActionHomeFragmentToBookDeliveryServicesOrderFragment action = HomeFragmentDirections.actionHomeFragmentToBookDeliveryServicesOrderFragment(data);
                            navController.navigate(action);
                            break;
                        }
                        case (int) ServicesGroup.DELIVERY_SERVICE_SEND_PACKAGES: {
                            HomeFragmentDirections.ActionHomeFragmentToBookDeliveryServicesSendPackageFragment action = HomeFragmentDirections.actionHomeFragmentToBookDeliveryServicesSendPackageFragment(data);
                            navController.navigate(action);
                            break;
                        }
                    }
                }
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
