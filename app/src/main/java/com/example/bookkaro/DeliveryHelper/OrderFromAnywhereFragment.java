package com.example.bookkaro.DeliveryHelper;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookkaro.AddressHelper.AddressModel;
import com.example.bookkaro.AddressHelper.SavedAddressFragment;
import com.example.bookkaro.AddressHelper.SearchAddressActivity;
import com.example.bookkaro.DeliveryHelper.Adapters.DeliveryItemAdapter;
import com.example.bookkaro.DeliveryHelper.Models.DeliveryItemModel;
import com.example.bookkaro.DeliveryHelper.Models.OrderFromAnywhereOrderModel;
import com.example.bookkaro.R;
import com.example.bookkaro.helper.AllOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class OrderFromAnywhereFragment extends Fragment {

    AutoCompleteTextView category;
    Button storeAddress, deliveryAddress;

    AddressModel deliveryAddressModel = new AddressModel();

    EditText txItemName;
    Button saveItem;

    FirebaseDatabase db;
    ArrayList<DeliveryItemModel> deliveryItemModelArrayList;
    RecyclerView itemsRecycler;

    EditText estimatedTotal, instructions;

    Button placeOrder;

    String serviceName;
    String serviceIcon;
    String fragmentFrom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_from_anywhere, container, false);

        db = FirebaseDatabase.getInstance();
        deliveryItemModelArrayList = new ArrayList<>();

        itemsRecycler = view.findViewById(R.id.order_from_anywhere_recycler);
        itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsRecycler.setNestedScrollingEnabled(false);

        storeAddress = view.findViewById(R.id.store_address_button);
        deliveryAddress = view.findViewById(R.id.delivery_address_button);
        estimatedTotal = view.findViewById(R.id.editTextEstimatedTotal);
        instructions = view.findViewById(R.id.editTextInstructions);
        category = view.findViewById(R.id.categories_dropdown);

        placeOrder = view.findViewById(R.id.any_store_next);

        Bundle bundle = getArguments();

        if(bundle.getString("fragmentFrom").equals("home")){
            serviceName = bundle.getString("serviceName");
            serviceIcon = bundle.getString("serviceIcon");
        }
        if(bundle.getString("fragmentFrom").equals("address")){
            deliveryAddressModel.setAddress(bundle.getString("address"));
            deliveryAddressModel.setAddressType(bundle.getString("addressType"));
            deliveryAddressModel.setApartmentNumber(bundle.getString("apartmentNumber"));
            deliveryAddressModel.setLandMark(bundle.getString("landMark"));
            deliveryAddressModel.setLatitude(bundle.getDouble("latitude"));
            deliveryAddressModel.setLongitude(bundle.getDouble("longitude"));
            category.setText(bundle.getString("category"));
            instructions.setText(bundle.getString("instructions"));
            estimatedTotal.setText(bundle.getString("estimatedTotal"));
            serviceName = bundle.getString("serviceName");
            serviceIcon = bundle.getString("serviceIcon");

            deliveryAddress.setText(deliveryAddressModel.getAddress().toString());
        }

        String[] CATEGORIES = new String[]{"Books","Groceries"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                R.layout.dropdown_menu_popup_item,
                CATEGORIES
        );

        category.setAdapter(adapter);

        deliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedAddressFragment savedAddressFragment = new SavedAddressFragment();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","orderFromAnywhere");
                if(category != null){
                    bundle.putString("category",category.getText().toString());
                    Log.i("category",category.getText().toString());
                }
                if(estimatedTotal != null){
                    bundle.putString("estimatedTotal",estimatedTotal.getText().toString());
                    Log.i("estimatedTotal",estimatedTotal.getText().toString());
                }
                if(instructions != null){
                    bundle.putString("instructions",instructions.getText().toString());
                    Log.i("instructions",instructions.getText().toString());
                }
                if(serviceName != null){
                    bundle.putString("serviceName",serviceName);
                }
                if(serviceIcon != null){
                    bundle.putString("serviceIcon",serviceIcon);
                }

                savedAddressFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,savedAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        storeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchAddressActivity.class);
                startActivity(intent);
            }
        });

        txItemName = view.findViewById(R.id.item_name_edit);
        saveItem = view.findViewById(R.id.addItem);

        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        db.getReference().child("orderFromAnywhereCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getSavedItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });

        return view;
    }

    private void getSavedItems() {
        db.getReference().child("orderFromAnywhereCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deliveryItemModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DeliveryItemModel deliveryItemModel = new DeliveryItemModel();
                    deliveryItemModel.setItemName(dataSnapshot.child("itemName").getValue().toString());
                    deliveryItemModel.setItemQuantity(dataSnapshot.child("itemQuantity").getValue().toString());
                    deliveryItemModel.setItemKey(dataSnapshot.getKey());

                    deliveryItemModelArrayList.add(deliveryItemModel);
                }

                DeliveryItemAdapter deliveryItemAdapter = new DeliveryItemAdapter(getContext(),deliveryItemModelArrayList, "orderFromAnywhere");
                itemsRecycler.setAdapter(deliveryItemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveItem() {

        String itemName = txItemName.getText().toString();

        HashMap<String, Object> item = new HashMap<>();
        item.put("itemName",itemName);
        item.put("itemQuantity","1");

        if(itemName.isEmpty()){
            Toast.makeText(getContext(),"Enter item name",Toast.LENGTH_SHORT).show();
            return;
        }

        txItemName.setText("");
        db.getReference().child("orderFromAnywhereCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).push().setValue(item);

    }

    private void saveOrder(){

        Date currentTime = Calendar.getInstance().getTime();
        String key = currentTime + FirebaseAuth.getInstance().getCurrentUser().getUid();

        String itemsCategory = category.getText().toString();
        String deliveryAddressString = deliveryAddress.getText().toString();
        String pickupAddressString = storeAddress.getText().toString();
        String estimatedTotalString = estimatedTotal.getText().toString();
        String instructionsString = instructions.getText().toString();

        OrderFromAnywhereOrderModel orderFromAnywhereOrderModel = new OrderFromAnywhereOrderModel();
        orderFromAnywhereOrderModel.setItemsCategory(itemsCategory);
        orderFromAnywhereOrderModel.setDeliveryAddress(deliveryAddressString);
        orderFromAnywhereOrderModel.setEstimatedTotal(estimatedTotalString);
        orderFromAnywhereOrderModel.setInstructions(instructionsString);
        orderFromAnywhereOrderModel.setPickupAddress(pickupAddressString);
        orderFromAnywhereOrderModel.setOrderItems(deliveryItemModelArrayList);
        orderFromAnywhereOrderModel.setOrderStatus(100);

        AllOrderModel allOrderModel = new AllOrderModel();
        allOrderModel.setServiceName(serviceName);
        allOrderModel.setServiceIcon(serviceIcon);
        allOrderModel.setKey(key);
        allOrderModel.setOrderType(1010);
        allOrderModel.setDateTime(currentTime.toString());

        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("orderFromAnywhereOrders").child(key).setValue(orderFromAnywhereOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                db.getReference().child("orderFromAnywhereCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                .child("allOrders").child(key).setValue(allOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                OrderFromAnywhereDetailsFragment orderFromAnywhereDetailsFragment = new OrderFromAnywhereDetailsFragment();

                                Bundle bundle = new Bundle();
                                bundle.putString("key",key);
                                orderFromAnywhereDetailsFragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, orderFromAnywhereDetailsFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
                    }
                });
            }
        });
    }
}