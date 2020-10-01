package com.example.bookkaro.ShopHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Adapters.ShopCategoryAdapter;
import com.example.bookkaro.ShopHelper.Models.ShopCategoryModel;
import com.example.bookkaro.ShopHelper.Models.ShopItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopItemsFragment extends Fragment {

    String shopType;
    String shopNumber;
    String shopName;
    String shopIcon;

    RecyclerView shopCategoryRecycler;

    ArrayList<ShopCategoryModel> shopCategoryModelArrayList;

    FirebaseDatabase db;

    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_items, container, false);

        Bundle bundle = getArguments();
        shopType = bundle.getString("shopType");
        shopNumber = bundle.getString("shopNumber");
        shopName = bundle.getString("shopName");
        shopIcon = bundle.getString("shopIcon");

        db = FirebaseDatabase.getInstance();
        shopCategoryModelArrayList = new ArrayList<>();
        shopCategoryRecycler = view.findViewById(R.id.categoriesRecycler);
        shopCategoryRecycler.setHasFixedSize(true);
        shopCategoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db.getReference().child("shops").child(shopType).child(shopType).child(shopNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopCartFragment shopCartFragment = new ShopCartFragment();

                Bundle bundle1 = new Bundle();
                bundle1.putString("shopType",shopType);
                bundle1.putString("shopNumber",shopNumber);
                bundle1.putString("shopName",shopName);
                bundle1.putString("shopIcon",shopIcon);
                bundle1.putString("fragmentFrom","shop");
                shopCartFragment.setArguments(bundle1);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,shopCartFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void getItems() {
        db.getReference().child("shops").child(shopType).child(shopNumber).child("Menu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopCategoryModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ShopCategoryModel shopCategoryModel = new ShopCategoryModel();
                    shopCategoryModel.setCategoryName(dataSnapshot.child("categoryName").getValue().toString());
                    GenericTypeIndicator<ArrayList<ShopItemModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ShopItemModel>>() {};
                    shopCategoryModel.setShopItemModelArrayList(dataSnapshot.child("items").getValue(genericTypeIndicator));

                    shopCategoryModelArrayList.add(shopCategoryModel);
                }

                ShopCategoryAdapter shopCategoryAdapter = new ShopCategoryAdapter(getContext(),shopCategoryModelArrayList, shopNumber);
                shopCategoryRecycler.setAdapter(shopCategoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}