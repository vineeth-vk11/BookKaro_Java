package com.example.bookkaro.HouseholdHelper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bookkaro.AddressHelper.SavedAddressFragment;
import com.example.bookkaro.HouseholdHelper.Adapters.HouseholdCartAdapter;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdCartModel;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdOrderModel;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdServiceModel;
import com.example.bookkaro.R;
import com.example.bookkaro.helper.AllOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class HouseholdCartFragment extends Fragment {

    FirebaseDatabase db;
    ArrayList<HouseholdCartModel> householdCartModelArrayList;
    RecyclerView cartRecycler;

    private Toolbar toolbar;

    Button dateSelector, timeSelector;
    TextView txtDate, txtTime;
    TextView txtItemTotal, txtOffer, finalAmount;

    int itemsTotal = 0;

    Button placeOrder;

    String serviceType;
    String serviceName;
    String serviceIcon;

    Button selectAddress;

    TextView address;

    String pincode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_household_cart, container, false);

        txtDate = view.findViewById(R.id.date);
        txtTime = view.findViewById(R.id.time);
        address = view.findViewById(R.id.addressText);
        finalAmount = view.findViewById(R.id.total_amount);

        Bundle bundle = getArguments();
        serviceType = bundle.getString("serviceType");
        serviceName = bundle.getString("serviceName");
        serviceIcon = bundle.getString("serviceIcon");

        if(bundle.getString("date") != null){
            txtDate.setText(bundle.getString("date"));
        }

        if(bundle.getString("time") != null){
            txtTime.setText(bundle.getString("time"));
        }

        if(bundle.getString("address") != null){
            address.setText(bundle.getString("address"));
        }
        if(bundle.getString("pincode")!=null){
            pincode = bundle.getString("pincode");
        }

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Household cart");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance();
        householdCartModelArrayList = new ArrayList<>();
        cartRecycler = view.findViewById(R.id.cartItemsRecycler);

        cartRecycler.setHasFixedSize(true);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecycler.setNestedScrollingEnabled(false);

        db.getReference().child("householdCarts").child(serviceType).child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCartItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        txtItemTotal = view.findViewById(R.id.item_total);
        txtOffer = view.findViewById(R.id.discount);

        dateSelector = view.findViewById(R.id.button_for_date);
        timeSelector = view.findViewById(R.id.button_for_time);

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });

        timeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        placeOrder = view.findViewById(R.id.button_for_payment);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeHouseholdOrder();
            }
        });

        selectAddress = view.findViewById(R.id.selectAddress);
        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = txtTime.getText().toString();
                String date = txtDate.getText().toString();

                SavedAddressFragment savedAddressFragment = new SavedAddressFragment();

                Bundle bundle1 = new Bundle();
                bundle1.putString("serviceName",serviceName);
                bundle1.putString("serviceIcon",serviceIcon);
                bundle1.putString("serviceType",serviceType);
                bundle1.putString("fragment","household");

                if(time!=null){
                    bundle1.putString("time",time);
                }
                if(date != null){
                    bundle1.putString("date",date);
                }

                savedAddressFragment.setArguments(bundle1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,savedAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void placeHouseholdOrder(){

        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        Date currentTime = Calendar.getInstance().getTime();
        String key = currentTime + FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(date.isEmpty()){
            Toast.makeText(getActivity(),"Please select a date",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(time.isEmpty()){
            Toast.makeText(getActivity(),"Please select a time",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(householdCartModelArrayList.size() == 0){
            Toast.makeText(getActivity(),"Please select items",Toast.LENGTH_SHORT).show();
            return;
        }

        HouseholdOrderModel householdOrderModel = new HouseholdOrderModel();
        householdOrderModel.setUserPhoneNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
        householdOrderModel.setDateOfService(date);
        householdOrderModel.setTimeOfService(time);
        householdOrderModel.setOrderItems(householdCartModelArrayList);
        householdOrderModel.setOrderTotal(itemsTotal);
        householdOrderModel.setOrderStatus(100);
        householdOrderModel.setVendorName("None");
        householdOrderModel.setVendorPhoneNumber("None");
        householdOrderModel.setOrderType(100);
        householdOrderModel.setDeliveryAddress(address.getText().toString());
        householdOrderModel.setPincode(pincode);

        AllOrderModel allOrderModel = new AllOrderModel();
        allOrderModel.setServiceName(serviceName);
        allOrderModel.setServiceIcon(serviceIcon);
        allOrderModel.setServiceTotal(itemsTotal);
        allOrderModel.setKey(key);
        allOrderModel.setOrderType(100);
        allOrderModel.setDateTime(currentTime.toString());

        db.getReference().child("householdOrders").child(serviceType).child("NewOrders").child(key).setValue(householdOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child("householdOrders").child(key).setValue(householdOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        db.getReference().child("householdCarts").child(serviceType).child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                        .child("allOrders").child(key).setValue(allOrderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HouseholdOrderDetailsFragment householdOrderDetailsFragment = new HouseholdOrderDetailsFragment();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("key",key);
                                        householdOrderDetailsFragment.setArguments(bundle);

                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                        fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,householdOrderDetailsFragment);
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
        db.getReference().child("householdCarts").child(serviceType).child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                householdCartModelArrayList.clear();
                itemsTotal = 0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    HouseholdCartModel householdCartModel = new HouseholdCartModel();
                    householdCartModel.setServiceName(dataSnapshot.child("serviceName").getValue().toString());
                    householdCartModel.setServicePrice(Integer.parseInt(dataSnapshot.child("servicePrice").getValue().toString()));
                    householdCartModel.setServiceQuantity(Integer.parseInt(dataSnapshot.child("serviceQuantity").getValue().toString()));
                    householdCartModel.setServiceId(Integer.parseInt(dataSnapshot.child("serviceId").getValue().toString()));

                    householdCartModelArrayList.add(householdCartModel);

                    itemsTotal += Integer.parseInt(dataSnapshot.child("servicePrice").getValue().toString())
                    * Integer.parseInt(dataSnapshot.child("serviceQuantity").getValue().toString());
                }

                HouseholdCartAdapter householdCartAdapter = new HouseholdCartAdapter(getContext(),householdCartModelArrayList,serviceType);
                cartRecycler.setAdapter(householdCartAdapter);
                txtItemTotal.setText(String.valueOf(itemsTotal));
                finalAmount.setText(String.valueOf(itemsTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void handleTimeButton(){
        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.get(Calendar.HOUR);
        int Minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute;
                txtTime.setText(time);
            }
        },Hour,Minute,false);
        timePickerDialog.show();
    }

    private void handleDateButton(){

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int date = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+ "/" +  month + "/" + year;
                txtDate.setText(date);
            }
        }, year, month, date);

        datePickerDialog.show();
    }
}