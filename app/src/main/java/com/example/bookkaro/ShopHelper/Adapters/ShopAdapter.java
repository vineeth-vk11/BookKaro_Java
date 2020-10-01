package com.example.bookkaro.ShopHelper.Adapters;

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

import com.example.bookkaro.HomeHelper.Models.LocationModel;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Models.ShopModel;
import com.example.bookkaro.ShopHelper.ShopItemsFragment;
import com.example.bookkaro.ShopHelper.ViewHolders.ShopViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    Context context;
    ArrayList<ShopModel> shopModelArrayList;
    ArrayList<LocationModel> locationModelArrayList;


    public ShopAdapter(Context context, ArrayList<ShopModel> shopModelArrayList) {
        this.context = context;
        this.shopModelArrayList = shopModelArrayList;
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_shop, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        String shopType = String.valueOf( shopModelArrayList.get(position).getShopType());
        String shopNumber = String.valueOf(shopModelArrayList.get(position).getShopNumber());
        String shopName = String.valueOf(shopModelArrayList.get(position).getShopName());
        String shopIcon = String.valueOf(shopModelArrayList.get(position).getShopIcon());

        holder.shopName.setText(shopModelArrayList.get(position).getShopName());
        holder.shopAddress.setText(shopModelArrayList.get(position).getShopAddress());
        Picasso.get().load(shopModelArrayList.get(position).getShopIcon()).into(holder.shopIcon);

        locationModelArrayList = shopModelArrayList.get(position).getLocationModelArrayList();
        for(int i = 0; i<locationModelArrayList.size();i++){
            Log.i("pincode",String.valueOf(locationModelArrayList.get(i).getPincode()));
        }

        holder.shopItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopItemsFragment shopItemsFragment = new ShopItemsFragment();

                Bundle bundle = new Bundle();
                bundle.putString("shopType",shopType);
                bundle.putString("shopNumber",shopNumber);
                bundle.putString("shopName",shopName);
                bundle.putString("shopIcon",shopIcon);
                shopItemsFragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, shopItemsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return shopModelArrayList.size();
    }
}
