package com.example.bookkaro.AddressHelper;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.DeliveryHelper.OrderFromAnywhereFragment;
import com.example.bookkaro.DeliveryHelper.SendPackagesFragment;
import com.example.bookkaro.HomeUi.HomeFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdCartFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.ShopCartFragment;

import java.util.ArrayList;

public class SavedAddressAdapter extends RecyclerView.Adapter<SavedAddressViewHolder> {
    Context context;
    ArrayList<AddressModel> addressModelArrayList;
    String fragment;
    String shopType;
    String shopNumber;
    String shopName;
    String shopIcon;

    String category;
    String estimatedTotal;
    String instructions;

    String serviceName;
    String serviceIcon;
    String serviceType;

    String date;
    String time;

    public SavedAddressAdapter(Context context, ArrayList<AddressModel> addressModelArrayList, String fragment, String shopType, String shopNumber, String shopName, String shopIcon, String category, String estimatedTotal, String instructions, String serviceName, String serviceIcon, String serviceType, String date, String time) {
        this.context = context;
        this.addressModelArrayList = addressModelArrayList;
        this.fragment = fragment;
        this.shopType = shopType;
        this.shopNumber = shopNumber;
        this.shopName = shopName;
        this.shopIcon = shopIcon;
        this.category = category;
        this.estimatedTotal = estimatedTotal;
        this.instructions = instructions;
        this.serviceName = serviceName;
        this.serviceIcon = serviceIcon;
        this.serviceType = serviceType;
        this.date = date;
        this.time = time;
    }

    @NonNull
    @Override
    public SavedAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_saved_address,parent,false);
        return new SavedAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAddressViewHolder holder, int position) {
        String addressType = addressModelArrayList.get(position).getAddressType();
        if(addressType == "Home"){
            holder.addressHead.setText(addressType);
            holder.addressTypeIcon.setImageResource(R.drawable.ic_address_home);
        }
        else if(addressType == "Work"){
            holder.addressHead.setText(addressType);
            holder.addressTypeIcon.setImageResource(R.drawable.ic_address_office);
        }
        else{
            holder.addressHead.setText(addressType);
            holder.addressTypeIcon.setImageResource(R.drawable.ic_location_on_a_light_bg);
        }
        holder.address.setText(addressModelArrayList.get(position).getAddress());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Fragment to",fragment);

                if(fragment.equals("orderFromAnywhere")){
                    Bundle data = new Bundle();
                    data.putString("address",addressModelArrayList.get(position).getAddress());
                    data.putDouble("latitude",addressModelArrayList.get(position).getLatitude());
                    data.putDouble("longitude",addressModelArrayList.get(position).getLongitude());
                    data.putString("apartmentNumber",addressModelArrayList.get(position).getApartmentNumber());
                    data.putString("landMark",addressModelArrayList.get(position).getLandMark());
                    data.putString("addressType",addressModelArrayList.get(position).getAddressType());
                    data.putString("category",category);
                    data.putString("estimatedTotal",estimatedTotal);
                    data.putString("instructions",instructions);
                    data.putString("serviceName",serviceName);
                    data.putString("serviceIcon",serviceIcon);
                    data.putString("fragmentFrom","address");

                    Log.i("bundleCategory",category);
                    Log.i("bundleTotal",estimatedTotal);
                    Log.i("bundleInstructions",instructions);

                    OrderFromAnywhereFragment orderFromAnywhereFragment = new OrderFromAnywhereFragment();
                    orderFromAnywhereFragment.setArguments(data);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, orderFromAnywhereFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

                else if(fragment.equals("sendPackages")){
                    Bundle data = new Bundle();
                    data.putString("address",addressModelArrayList.get(position).getAddress());
                    data.putDouble("latitude",addressModelArrayList.get(position).getLatitude());
                    data.putDouble("longitude",addressModelArrayList.get(position).getLongitude());
                    data.putString("apartmentNumber",addressModelArrayList.get(position).getApartmentNumber());
                    data.putString("landMark",addressModelArrayList.get(position).getLandMark());
                    data.putString("addressType",addressModelArrayList.get(position).getAddressType());
                    data.putString("serviceName",serviceName);
                    data.putString("serviceIcon",serviceIcon);
                    data.putString("fragmentFrom","address");
                    data.putString("instructions",instructions);

                    SendPackagesFragment sendPackagesFragment = new SendPackagesFragment();
                    sendPackagesFragment.setArguments(data);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, sendPackagesFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else if(fragment.equals("shopCart")){
                    Bundle data1 = new Bundle();
                    data1.putString("address",addressModelArrayList.get(position).getAddress());
                    data1.putDouble("latitude",addressModelArrayList.get(position).getLatitude());
                    data1.putDouble("longitude",addressModelArrayList.get(position).getLongitude());
                    data1.putString("apartmentNumber",addressModelArrayList.get(position).getApartmentNumber());
                    data1.putString("landMark",addressModelArrayList.get(position).getLandMark());
                    data1.putString("addressType",addressModelArrayList.get(position).getAddressType());
                    data1.putString("shopType",shopType);
                    data1.putString("shopNumber",shopNumber);
                    data1.putString("shopIcon",shopIcon);
                    data1.putString("shopName",shopName);
                    data1.putString("fragmentFrom","address");

                    Log.i("sShopName",shopName);
                    Log.i("sShopIcon",shopIcon);

                    ShopCartFragment shopCartFragment = new ShopCartFragment();
                    shopCartFragment.setArguments(data1);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, shopCartFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
                else if(fragment.equals("household")){
                    Bundle data1 = new Bundle();
                    data1.putString("address",addressModelArrayList.get(position).getAddress());
                    data1.putDouble("latitude",addressModelArrayList.get(position).getLatitude());
                    data1.putDouble("longitude",addressModelArrayList.get(position).getLongitude());
                    data1.putString("apartmentNumber",addressModelArrayList.get(position).getApartmentNumber());
                    data1.putString("landMark",addressModelArrayList.get(position).getLandMark());
                    data1.putString("addressType",addressModelArrayList.get(position).getAddressType());
                    data1.putString("pincode",addressModelArrayList.get(position).getPincode());
                    data1.putString("serviceName",serviceName);
                    data1.putString("serviceIcon",serviceIcon);
                    data1.putString("serviceType",serviceType);
                    data1.putString("date",date);
                    data1.putString("time",time);

                    HouseholdCartFragment householdCartFragment = new HouseholdCartFragment();
                    householdCartFragment.setArguments(data1);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, householdCartFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

                else if(fragment.equals("home")){
                    Bundle data1 = new Bundle();
                    data1.putString("address",addressModelArrayList.get(position).getAddress());
                    data1.putDouble("latitude",addressModelArrayList.get(position).getLatitude());
                    data1.putDouble("longitude",addressModelArrayList.get(position).getLongitude());
                    data1.putString("apartmentNumber",addressModelArrayList.get(position).getApartmentNumber());
                    data1.putString("landMark",addressModelArrayList.get(position).getLandMark());
                    data1.putString("addressType",addressModelArrayList.get(position).getAddressType());
                    data1.putString("pincode",addressModelArrayList.get(position).getPincode());

                    HomeFragment homeFragment = new HomeFragment();
                    homeFragment.setArguments(data1);

                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, homeFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelArrayList.size();
    }
}
