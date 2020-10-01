package com.example.bookkaro.ShopHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookkaro.AddressHelper.AddressModel;
import com.example.bookkaro.AddressHelper.SavedAddressFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Adapters.ShopCartAdapter;
import com.example.bookkaro.ShopHelper.Adapters.ShopCategoryItemAdapter;
import com.example.bookkaro.ShopHelper.Models.ShopCartModel;
import com.example.bookkaro.ShopHelper.Models.ShopOrderItemModel;
import com.example.bookkaro.ShopHelper.Models.ShopOrderModel;
import com.example.bookkaro.helper.AllOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShopCartFragment extends Fragment {

    String shopType;
    String shopNumber;
    String shopName;
    String shopIcon;

    FirebaseDatabase db;
    ArrayList<ShopCartModel> shopCartModelArrayList;

    RecyclerView cartRecycler;

    Button selectAddress;

    AddressModel deliveryAddressModel = new AddressModel();
    TextView deliveryAddress;

    int itemsTotal;
    TextView itemTotal, finalAmount;

    Button placeOrder;

    ArrayList<ShopOrderItemModel> shopOrderItemModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);

        deliveryAddress = view.findViewById(R.id.delivery_address);
        itemTotal = view.findViewById(R.id.item_total);
        finalAmount = view.findViewById(R.id.total_amount);

        Bundle bundle = getArguments();

        if(bundle.getString("fragmentFrom").equals("shop")){
            shopType = bundle.getString("shopType");
            shopNumber = bundle.getString("shopNumber");
            shopName = bundle.getString("shopName");
            shopIcon = bundle.getString("shopIcon");

            Log.i("ShopName",shopName);
            Log.i("ShopIcon",shopIcon);
        }

        if(bundle.getString("fragmentFrom").equals("address")){
            deliveryAddressModel.setAddress(bundle.getString("address"));
            deliveryAddressModel.setAddressType(bundle.getString("addressType"));
            deliveryAddressModel.setApartmentNumber(bundle.getString("apartmentNumber"));
            deliveryAddressModel.setLandMark(bundle.getString("landMark"));
            deliveryAddressModel.setLatitude(bundle.getDouble("latitude"));
            deliveryAddressModel.setLongitude(bundle.getDouble("longitude"));
            shopType = bundle.getString("shopType",shopType);
            shopName = bundle.getString("shopName",shopName);
            shopIcon = bundle.getString("shopIcon",shopIcon);
            shopNumber = bundle.getString("shopNumber",shopNumber);

            Log.i("rShopName",shopName);
            Log.i("rShopIcon",shopIcon);

            deliveryAddress.setText(deliveryAddressModel.getAddress().toString());
        }

        cartRecycler = view.findViewById(R.id.placedItemsRecycler);
        cartRecycler.setHasFixedSize(true);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecycler.setNestedScrollingEnabled(false);

        shopCartModelArrayList = new ArrayList<>();
        shopOrderItemModelArrayList = new ArrayList<>();

        db = FirebaseDatabase.getInstance();
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("shopCart")
                .child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsTotal = 0;
                getCartItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectAddress = view.findViewById(R.id.add_address);
        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedAddressFragment savedAddressFragment = new SavedAddressFragment();

                Bundle bundle1 = new Bundle();
                bundle1.putString("fragment","shopCart");
                bundle1.putString("shopType",shopType);
                bundle1.putString("shopNumber",shopNumber);
                bundle1.putString("shopName",shopName);
                bundle1.putString("shopIcon",shopIcon);

                savedAddressFragment.setArguments(bundle1);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, savedAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        placeOrder = view.findViewById(R.id.button_for_payment);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
        return view;
    }

    private void placeOrder() {

        Date currentTime = Calendar.getInstance().getTime();
        String key = currentTime + FirebaseAuth.getInstance().getCurrentUser().getUid();

        ShopOrderModel shopOrderModel = new ShopOrderModel();
        shopOrderModel.setUserAddress(deliveryAddressModel);

        for(int i = 0; i<shopCartModelArrayList.size();i++){

            ShopOrderItemModel shopOrderItemModel = new ShopOrderItemModel();
            shopOrderItemModel.setItemName(shopCartModelArrayList.get(i).getItemName());
            shopOrderItemModel.setItemId(shopCartModelArrayList.get(i).getItemId());
            shopOrderItemModel.setItemPrice(shopCartModelArrayList.get(i).getItemPrice());
            shopOrderItemModel.setItemQuantity(shopCartModelArrayList.get(i).getItemQuantity());
            shopOrderItemModel.setItemDesc(shopCartModelArrayList.get(i).getItemDesc());
            shopOrderItemModel.setItemIcon(shopCartModelArrayList.get(i).getItemIcon());

            shopOrderItemModelArrayList.add(shopOrderItemModel);
        }

        shopOrderModel.setItems(shopOrderItemModelArrayList);
        shopOrderModel.setOrderStatus(100);
        shopOrderModel.setOrderTotal(itemsTotal);
        shopOrderModel.setOrderType(102);
        shopOrderModel.setVendorName(shopName);
        shopOrderModel.setVendorPhoneNumber(shopNumber);
        shopOrderModel.setUserPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        AllOrderModel allOrderModel = new AllOrderModel();
        allOrderModel.setOrderType(102);
        allOrderModel.setServiceName(shopName);
        allOrderModel.setKey(key);
        allOrderModel.setServiceIcon(shopIcon);
        allOrderModel.setDateTime(currentTime.toString());

        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("shopOrders").child(key).setValue(shopOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child("allOrders").child(key).setValue(allOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        db.getReference().child("shopOrders").child(shopNumber).child("Orders").child("NewOrders").child(key).setValue(shopOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                        .child("shopCart").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        ShopOrderDetailsFragment shopOrderDetailsFragment = new ShopOrderDetailsFragment();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("key",key);
                                        shopOrderDetailsFragment.setArguments(bundle);

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, shopOrderDetailsFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void getCartItems() {
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("shopCart")
                .child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopCartModelArrayList.clear();
                itemsTotal = 0;
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    ShopCartModel shopCartModel = new ShopCartModel();

                    String itemPrice = snapshot1.child("itemPrice").getValue().toString();
                    int itemQuantity = Integer.parseInt(snapshot1.child("itemQuantity").getValue().toString());

                    shopCartModel.setItemName(snapshot1.child("itemName").getValue().toString());
                    shopCartModel.setItemPrice(snapshot1.child("itemPrice").getValue().toString());
                    shopCartModel.setItemQuantity(Integer.parseInt(snapshot1.child("itemQuantity").getValue().toString()));
                    shopCartModel.setItemDesc(snapshot1.child("itemDesc").getValue().toString());
                    shopCartModel.setItemIcon(snapshot1.child("itemIcon").getValue().toString());
                    shopCartModel.setItemId(snapshot1.child("itemId").getValue().toString());

                    itemsTotal += Integer.parseInt(itemPrice) * itemQuantity;
                    shopCartModelArrayList.add(shopCartModel);
                }

                itemTotal.setText(String.valueOf(itemsTotal));
                finalAmount.setText(String.valueOf(itemsTotal));
                ShopCartAdapter shopCartAdapter = new ShopCartAdapter(getContext(),shopCartModelArrayList);
                cartRecycler.setAdapter(shopCartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}