package com.example.bookkaro.BookingsHelper;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.DeliveryHelper.OrderFromAnywhereDetailsFragment;
import com.example.bookkaro.DeliveryHelper.SendPackageOrderDetailsFragment;
import com.example.bookkaro.DeliveryHelper.SendPackagesFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdOrderDetailsFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.ShopOrderDetailsFragment;
import com.example.bookkaro.helper.AllOrderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsViewHolder> {

    Context context;
    ArrayList<AllOrderModel> allOrderModelArrayList;

    public BookingsAdapter(Context context, ArrayList<AllOrderModel> allOrderModelArrayList) {
        this.context = context;
        this.allOrderModelArrayList = allOrderModelArrayList;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_my_bookings, parent, false);
        return new BookingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {

        int orderType = allOrderModelArrayList.get(position).getOrderType();
        String key = allOrderModelArrayList.get(position).getKey();

        holder.serviceName.setText(allOrderModelArrayList.get(position).getServiceName());
        holder.dateTime.setText(allOrderModelArrayList.get(position).getDateTime());
        Picasso.get().load(allOrderModelArrayList.get(position).getServiceIcon()).into(holder.serviceIcon);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderType == 100){
                    HouseholdOrderDetailsFragment householdOrderDetailsFragment = new HouseholdOrderDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("key",key);

                    householdOrderDetailsFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,householdOrderDetailsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
                if(orderType == 1010){
                    OrderFromAnywhereDetailsFragment orderFromAnywhereDetailsFragment = new OrderFromAnywhereDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("key",key);

                    orderFromAnywhereDetailsFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,orderFromAnywhereDetailsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                if(orderType == 1011){

                    SendPackageOrderDetailsFragment sendPackageOrderDetailsFragment = new SendPackageOrderDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("key",key);

                    sendPackageOrderDetailsFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,sendPackageOrderDetailsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
                if(orderType == 102){
                    ShopOrderDetailsFragment shopOrderDetailsFragment = new ShopOrderDetailsFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("key",key);

                    shopOrderDetailsFragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,shopOrderDetailsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return allOrderModelArrayList.size();
    }
}
