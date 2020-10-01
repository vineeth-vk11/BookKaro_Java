package com.example.bookkaro.HomeHelper.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.DeliveryHelper.OrderFromAnywhereFragment;
import com.example.bookkaro.DeliveryHelper.SendPackagesFragment;
import com.example.bookkaro.HomeHelper.Models.LocationModel;
import com.example.bookkaro.HomeHelper.Models.ServiceModel;
import com.example.bookkaro.HomeHelper.ViewHolders.ServiceViewHolder;
import com.example.bookkaro.HouseholdHelper.HouseholdFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdServicesFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.ShopsFragment;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder> {

    Context context;
    ArrayList<ServiceModel> serviceModelArrayList;
    String pincode;

    ArrayList<LocationModel> locationModelArrayList = new ArrayList<>();

    public ServiceAdapter(Context context, ArrayList<ServiceModel> serviceModelArrayList, String pincode) {
        this.context = context;
        this.serviceModelArrayList = serviceModelArrayList;
        this.pincode = pincode;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_category,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        int status = 0;
        int serviceCategory = serviceModelArrayList.get(position).getServiceCategoryId();
        int serviceId = serviceModelArrayList.get(position).getServiceId();
        String serviceName = serviceModelArrayList.get(position).getServiceName();
        String serviceIcon = serviceModelArrayList.get(position).getServiceIcon();

        Log.i("serviceId",String.valueOf(serviceId));
        Log.i("serviceName",serviceName);
        Log.i("serviceIcon",serviceIcon);

        holder.serviceName.setText(serviceModelArrayList.get(position).getServiceName());
        Picasso.get().load(serviceModelArrayList.get(position).getServiceIcon()).into(holder.serviceIcon);

        locationModelArrayList =  serviceModelArrayList.get(position).getLocations();
        for(int i = 0; i<locationModelArrayList.size(); i++){
            Log.i("pincode",pincode);
            Log.i("code",String.valueOf(locationModelArrayList.get(i).getPincode()));
            if(pincode.equals(String.valueOf(locationModelArrayList.get(i).getPincode()))){
                status = 1;
                break;
            }
            else {
                status = 0;
            }
        }

        if(String.valueOf(status).equals("0")){
            holder.availability.setVisibility(View.INVISIBLE);
            holder.serviceCard.setCardBackgroundColor(Color.parseColor("#CDCDCD"));
            holder.serviceCard.setClickable(false);
        }
        int finalStatus = status;
        holder.serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("id",String.valueOf(serviceId));
                if(finalStatus == 0){
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Toast.makeText(activity,"Service not available in your area",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(String.valueOf(serviceModelArrayList.get(position).getServiceCategoryId()).equals("100")){

                        HouseholdFragment householdFragment = new HouseholdFragment();
                        HouseholdServicesFragment householdServicesFragment = new HouseholdServicesFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("serviceId", String.valueOf(serviceId));
                        bundle.putString("serviceName",serviceName);
                        bundle.putString("serviceIcon",serviceIcon);

                        Log.i("serviceId is put",String.valueOf(serviceId));
                        householdFragment.setArguments(bundle);
                        householdServicesFragment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, householdFragment );
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if(String.valueOf(serviceId).equals("1010")){
                        Log.i("Entered","true");
                        OrderFromAnywhereFragment orderFromAnywhereFragment = new OrderFromAnywhereFragment();

                        Bundle bundle= new Bundle();
                        bundle.putString("serviceName",serviceName);
                        bundle.putString("serviceIcon",serviceIcon);
                        bundle.putString("fragmentFrom","home");
                        orderFromAnywhereFragment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, orderFromAnywhereFragment );
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if(String.valueOf(serviceId).equals("1011")){
                        SendPackagesFragment sendPackagesFragment = new SendPackagesFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("serviceName",serviceName);
                        bundle.putString("serviceIcon",serviceIcon);
                        bundle.putString("fragmentFrom","home");

                        sendPackagesFragment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, sendPackagesFragment );
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }

                    else if(String.valueOf(serviceModelArrayList.get(position).getServiceCategoryId()).equals("102")){
                        ShopsFragment shopsFragment = new ShopsFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("shopType", String.valueOf(serviceId));
                        shopsFragment.setArguments(bundle);

                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, shopsFragment );
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if(String.valueOf(serviceModelArrayList.get(position).getServiceCategoryId()).equals("0")){
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Toast.makeText(activity,"Service not available in your area",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return serviceModelArrayList.size();
    }
}
