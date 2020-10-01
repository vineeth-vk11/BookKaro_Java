package com.example.bookkaro.DeliveryHelper;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookkaro.AddressHelper.AddressModel;
import com.example.bookkaro.AddressHelper.SavedAddressFragment;
import com.example.bookkaro.DeliveryHelper.Adapters.DeliveryItemAdapter;
import com.example.bookkaro.DeliveryHelper.Models.DeliveryItemModel;
import com.example.bookkaro.DeliveryHelper.Models.SendPackageModel;
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

public class SendPackagesFragment extends Fragment {

    Button pickupAddress, deliveryAddress;

    EditText txtItemName;
    Button addItem;

    FirebaseDatabase db;

    ArrayList<DeliveryItemModel> deliveryItemModelArrayList;
    RecyclerView itemsRecycler;

    EditText instructions;

    Button next;

    AddressModel deliveryAddressModel = new AddressModel();

    String serviceName;
    String serviceIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_packages, container, false);

        pickupAddress = view.findViewById(R.id.pickup_address_button);
        deliveryAddress = view.findViewById(R.id.SEND_delivery_address_button);
        txtItemName = view.findViewById(R.id.item_name_edit);
        addItem = view.findViewById(R.id.addItem);
        instructions = view.findViewById(R.id.SEND_editTextInstructions);
        next= view.findViewById(R.id.send_package_next);

        Bundle bundle = getArguments();
        if(getArguments()!=null){
            if(bundle.getString("fragmentFrom").equals("home")){
                serviceIcon = bundle.getString("serviceIcon");
                serviceName = bundle.getString("serviceName");
            }
            if(bundle.getString("fragmentFrom").equals("address")){
                deliveryAddressModel.setAddress(bundle.getString("address"));
                deliveryAddressModel.setAddressType(bundle.getString("addressType"));
                deliveryAddressModel.setApartmentNumber(bundle.getString("apartmentNumber"));
                deliveryAddressModel.setLandMark(bundle.getString("landMark"));
                deliveryAddressModel.setLatitude(bundle.getDouble("latitude"));
                deliveryAddressModel.setLongitude(bundle.getDouble("longitude"));
                serviceName = bundle.getString("serviceName");
                serviceIcon = bundle.getString("serviceIcon");
                instructions.setText(bundle.getString("instructions"));

                deliveryAddress.setText(deliveryAddressModel.getAddress().toString());
            }
        }

        db = FirebaseDatabase.getInstance();
        deliveryItemModelArrayList = new ArrayList<>();

        itemsRecycler = view.findViewById(R.id.sendPackageItems);
        itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsRecycler.setNestedScrollingEnabled(false);

        pickupAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedAddressFragment savedAddressFragment = new SavedAddressFragment();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","sendPackages");
                bundle.putString("type","pickup");
                bundle.putString("serviceIcon",serviceIcon);
                bundle.putString("serviceName",serviceName);
                if(instructions.getText().toString() != null){
                    bundle.putString("instructions",instructions.getText().toString());
                }

                savedAddressFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,savedAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        db.getReference().child("sendPackagesCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getSavedItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        return view;
    }

    private void getSavedItems() {
        db.getReference().child("sendPackagesCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
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

                DeliveryItemAdapter deliveryItemAdapter = new DeliveryItemAdapter(getContext(),deliveryItemModelArrayList,"sendPackage");
                itemsRecycler.setAdapter(deliveryItemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveItem() {
        String itemName = txtItemName.getText().toString();

        HashMap<String, Object> item = new HashMap<>();
        item.put("itemName",itemName);
        item.put("itemQuantity","1");

        if(itemName.isEmpty()){
            Toast.makeText(getContext(),"Enter item name",Toast.LENGTH_SHORT).show();
            return;
        }

        txtItemName.setText("");
        db.getReference().child("sendPackagesCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).push().setValue(item);
    }

    private void placeOrder(){

        Date currentTime = Calendar.getInstance().getTime();
        String key = currentTime + FirebaseAuth.getInstance().getCurrentUser().getUid();

        SendPackageModel sendPackageModel = new SendPackageModel();
        sendPackageModel.setUserPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        sendPackageModel.setDeliveryAddress(deliveryAddress.getText().toString());
        sendPackageModel.setInstructions(instructions.getText().toString());
        sendPackageModel.setOrderItems(deliveryItemModelArrayList);
        sendPackageModel.setOrderStatus(100);

        AllOrderModel allOrderModel = new AllOrderModel();
        allOrderModel.setServiceName(serviceName);
        allOrderModel.setServiceIcon(serviceIcon);
        allOrderModel.setOrderType(1011);
        allOrderModel.setKey(key);
        allOrderModel.setDateTime(currentTime.toString());

        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("sendPackageOrders").child(key).setValue(sendPackageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                db.getReference().child("sendPackagesCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                .child("allOrders").child(key).setValue(allOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                SendPackageOrderDetailsFragment sendPackageOrderDetailsFragment = new SendPackageOrderDetailsFragment();

                                Bundle bundle = new Bundle();
                                bundle.putString("key",key);
                                sendPackageOrderDetailsFragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, sendPackageOrderDetailsFragment);
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