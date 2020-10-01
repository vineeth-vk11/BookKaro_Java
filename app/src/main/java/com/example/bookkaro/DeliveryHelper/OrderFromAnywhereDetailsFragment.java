package com.example.bookkaro.DeliveryHelper;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.example.bookkaro.DeliveryHelper.Adapters.DeliveryOrderDetailsAdapter;
import com.example.bookkaro.DeliveryHelper.Models.DeliveryItemModel;
import com.example.bookkaro.HomeUi.MyBookingsFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdCustomerReviewsFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdPackagesFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdServicesFragment;
import com.example.bookkaro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrderFromAnywhereDetailsFragment extends Fragment {

    FirebaseDatabase db;

    ArrayList<DeliveryItemModel> deliveryItemModelArrayList;

    RecyclerView itemsRecycler;

    VerticalStepView orderDetails100;
    VerticalStepView orderDetails101;
    VerticalStepView orderDetails102;
    VerticalStepView orderDetails103;
    VerticalStepView orderDetails104;

    String key;

    String deliveryPersonName;
    String deliveryPersonPhoneNumber;

    private Toolbar toolbar;

    TextView deliveryAddress;

    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_from_anywhere_details, container, false);

        Bundle bundle = getArguments();
        key = bundle.getString("key");

        toolbar = view.findViewById(R.id.toolbar);
        next = view.findViewById(R.id.nextButton);

        toolbar.setTitle("Order details");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        deliveryItemModelArrayList = new ArrayList<>();

        itemsRecycler = view.findViewById(R.id.orderDetailsRecycler);
        itemsRecycler.setNestedScrollingEnabled(false);
        itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        orderDetails100 = view.findViewById(R.id.orderStatus100);
        orderDetails101 = view.findViewById(R.id.orderStatus101);
        orderDetails102 = view.findViewById(R.id.orderStatus102);
        orderDetails103 = view.findViewById(R.id.orderStatus103);
        orderDetails104 = view.findViewById(R.id.orderStatus104);

        deliveryAddress = view.findViewById(R.id.deliveryAddress);

        db = FirebaseDatabase.getInstance();
        getOrderDetails();
        getItems();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBookingsFragment myBookingsFragment = new MyBookingsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment, myBookingsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void getItems() {
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("orderFromAnywhereOrders").child(key).child("orderItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    DeliveryItemModel deliveryItemModel = new DeliveryItemModel();
                    deliveryItemModel.setItemName(dataSnapshot.child("itemName").getValue().toString());
                    deliveryItemModel.setItemQuantity(dataSnapshot.child("itemQuantity").getValue().toString());

                    deliveryItemModelArrayList.add(deliveryItemModel);
                }

                DeliveryOrderDetailsAdapter deliveryOrderDetailsAdapter = new DeliveryOrderDetailsAdapter(getContext(),deliveryItemModelArrayList);
                itemsRecycler.setAdapter(deliveryOrderDetailsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOrderDetails(){
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("orderFromAnywhereOrders").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int orderStatus = Integer.parseInt(snapshot.child("orderStatus").getValue().toString());
                deliveryPersonName = "XYZ";
                deliveryPersonPhoneNumber = "8978117894";
                deliveryAddress.setText(snapshot.child("deliveryAddress").getValue().toString());

                if(orderStatus == 100){
                    setupStepperFor100();
                }
                else if(orderStatus == 101){
                    setupStepperFor101();
                }
                else if(orderStatus == 102){
                    setupStepperFor102();
                }
                else if(orderStatus == 103){
                    setupStepperFor103();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupStepperFor100(){
        orderDetails100.setVisibility(View.VISIBLE);
        orderDetails101.setVisibility(View.INVISIBLE);
        orderDetails102.setVisibility(View.INVISIBLE);
        orderDetails103.setVisibility(View.INVISIBLE);
        orderDetails104.setVisibility(View.INVISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Looking for delivery persons");
        details.add("Order picked up");
        details.add("order delivered");

        orderDetails100
                .setStepsViewIndicatorComplectingPosition(details.size()-3)
                .setStepViewTexts(details)
                .reverseDraw(false)
                .setTextSize(18)
                .setLinePaddingProportion(0.85f)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#1B8700"))
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
                .setStepViewUnComplectedTextColor(Color.parseColor("#000000"))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#d32f2f"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_done_24))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_warning_24));
    }

    private void setupStepperFor101(){
        orderDetails100.setVisibility(View.INVISIBLE);
        orderDetails101.setVisibility(View.VISIBLE);
        orderDetails102.setVisibility(View.INVISIBLE);
        orderDetails103.setVisibility(View.INVISIBLE);
        orderDetails104.setVisibility(View.INVISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order assigned to " + deliveryPersonName + ","+ deliveryPersonPhoneNumber);
        details.add("waiting for delivery person to pickup the order");
        details.add("order delivered");

        orderDetails101
                .setStepsViewIndicatorComplectingPosition(details.size()-2)
                .setStepViewTexts(details)
                .reverseDraw(false)
                .setTextSize(18)
                .setLinePaddingProportion(0.85f)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#1B8700"))
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
                .setStepViewUnComplectedTextColor(Color.parseColor("#000000"))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#d32f2f"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_done_24))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_warning_24));
    }

    private void setupStepperFor102(){
        orderDetails100.setVisibility(View.INVISIBLE);
        orderDetails101.setVisibility(View.INVISIBLE);
        orderDetails102.setVisibility(View.VISIBLE);
        orderDetails103.setVisibility(View.INVISIBLE);
        orderDetails104.setVisibility(View.INVISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order assigned to " + deliveryPersonName + ","+ deliveryPersonPhoneNumber);
        details.add("Order picked up");
        details.add("waiting for order to be delivered");

        orderDetails102
                .setStepsViewIndicatorComplectingPosition(details.size()-1)
                .setStepViewTexts(details)
                .reverseDraw(false)
                .setTextSize(18)
                .setLinePaddingProportion(0.85f)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#1B8700"))
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
                .setStepViewUnComplectedTextColor(Color.parseColor("#000000"))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#d32f2f"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_done_24))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_warning_24));
    }

    private void setupStepperFor103(){
        orderDetails100.setVisibility(View.INVISIBLE);
        orderDetails101.setVisibility(View.INVISIBLE);
        orderDetails102.setVisibility(View.INVISIBLE);
        orderDetails103.setVisibility(View.VISIBLE);
        orderDetails104.setVisibility(View.INVISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order assigned to " + deliveryPersonName + ","+ deliveryPersonPhoneNumber);
        details.add("Order picked up");
        details.add("Order delivered");

        orderDetails103
                .setStepsViewIndicatorComplectingPosition(details.size())
                .setStepViewTexts(details)
                .reverseDraw(false)
                .setLinePaddingProportion(0.85f)
                .setTextSize(18)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#1B8700"))
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
                .setStepViewUnComplectedTextColor(Color.parseColor("#000000"))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#d32f2f"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_done_24))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_warning_24));
    }

    private void setupStepperFor104(){
        orderDetails100.setVisibility(View.INVISIBLE);
        orderDetails101.setVisibility(View.INVISIBLE);
        orderDetails102.setVisibility(View.INVISIBLE);
        orderDetails103.setVisibility(View.INVISIBLE);
        orderDetails104.setVisibility(View.VISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order Accepted");
        details.add("Order assigned to " + deliveryPersonName + ","+ deliveryPersonPhoneNumber);
        details.add("Order picked up");
        details.add("order delivered");

        orderDetails104
                .setStepsViewIndicatorComplectingPosition(details.size())
                .setStepViewTexts(details)
                .reverseDraw(false)
                .setLinePaddingProportion(0.85f)
                .setTextSize(18)
                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#1B8700"))
                .setStepViewComplectedTextColor(Color.parseColor("#000000"))
                .setStepViewUnComplectedTextColor(Color.parseColor("#000000"))
                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#d32f2f"))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_done_24))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_warning_24));
    }
}