package com.example.bookkaro.ShopHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookkaro.HomeHelper.Models.LocationModel;
import com.example.bookkaro.HomeHelper.Models.ServiceModel;
import com.example.bookkaro.HouseholdHelper.HouseholdCustomerReviewsFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdPackagesFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdServicesFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Adapters.ShopAdapter;
import com.example.bookkaro.ShopHelper.Models.ShopModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ShopsFragment extends Fragment {

    FirebaseDatabase db;
    RecyclerView shopRecycler;
    String shopType;

    ArrayList<ShopModel> shopModelArrayList;

    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        
        toolbar.setTitle("Shops");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        Bundle bundle = getArguments();
        shopType = bundle.getString("shopType");

        db = FirebaseDatabase.getInstance();
        shopModelArrayList = new ArrayList<>();

        shopRecycler = view.findViewById(R.id.shopsRecycler);
        shopRecycler.setHasFixedSize(true);
        shopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db.getReference().child("shopServices").child(shopType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getShops();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void getShops() {
        db.getReference().child("shopServices").child(shopType).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ShopModel shopModel = new ShopModel();
                    shopModel.setShopName(dataSnapshot.child("shopName").getValue().toString());
                    shopModel.setShopIcon(dataSnapshot.child("shopIcon").getValue().toString());
                    shopModel.setShopAddress(dataSnapshot.child("shopAddress").getValue().toString());
                    shopModel.setShopNumber(dataSnapshot.child("shopNumber").getValue().toString());
                    shopModel.setShopType(Integer.parseInt(dataSnapshot.child("shopType").getValue().toString()));
                    GenericTypeIndicator<ArrayList<LocationModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<LocationModel>>() {};
                    shopModel.setLocationModelArrayList(dataSnapshot.child("locations").getValue(genericTypeIndicator));

                    shopModelArrayList.add(shopModel);
                }

                ShopAdapter shopAdapter = new ShopAdapter(getContext(),shopModelArrayList);
                shopRecycler.setAdapter(shopAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}