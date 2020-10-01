package com.example.bookkaro.ShopHelper;

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
import com.example.bookkaro.AddressHelper.AddressModel;
import com.example.bookkaro.HomeUi.MyBookingsFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdCustomerReviewsFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdPackagesFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdServicesFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Adapters.ShopOrderDetailsAdapter;
import com.example.bookkaro.ShopHelper.Models.ShopOrderItemModel;
import com.example.bookkaro.ShopHelper.Models.ShopOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderDetailsFragment extends Fragment {

    FirebaseDatabase db;

    ArrayList<ShopOrderItemModel> shopOrderItemModelArrayList;

    RecyclerView orderItemsRecycler;

    String key;

    VerticalStepView orderDetails100;
    VerticalStepView orderDetails101;
    VerticalStepView orderDetails102;
    VerticalStepView orderDetails103;
    VerticalStepView orderDetails104;


    String deliveryPersonName;
    String deliveryPersonPhoneNumber;

    TextView orderTotal;
    TextView deliveryAddress;

    private Toolbar toolbar;

    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_order_details, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        deliveryAddress = view.findViewById(R.id.deliveryAddress);
        next = view.findViewById(R.id.nextButton);

        toolbar.setTitle("Order Details");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        orderDetails100 = view.findViewById(R.id.orderStatus100);
        orderDetails101 = view.findViewById(R.id.orderStatus101);
        orderDetails102 = view.findViewById(R.id.orderStatus102);
        orderDetails103 = view.findViewById(R.id.orderStatus103);
        orderDetails104 = view.findViewById(R.id.orderStatus104);


        Bundle bundle = getArguments();
        key = bundle.getString("key");

        orderTotal = view.findViewById(R.id.orderTotal);

        shopOrderItemModelArrayList = new ArrayList<>();
        orderItemsRecycler = view.findViewById(R.id.orderDetailsRecycler);
        orderItemsRecycler.setHasFixedSize(true);
        orderItemsRecycler.setNestedScrollingEnabled(false);
        orderItemsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseDatabase.getInstance();
        getOrderItems();
        getOrderDetails();

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

    private void getOrderItems() {
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("shopOrders").child(key).child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ShopOrderItemModel shopOrderItemModel = new ShopOrderItemModel();
                    shopOrderItemModel.setItemName(dataSnapshot.child("itemName").getValue().toString());
                    shopOrderItemModel.setItemPrice(dataSnapshot.child("itemPrice").getValue().toString());
                    shopOrderItemModel.setItemQuantity(Integer.parseInt(dataSnapshot.child("itemQuantity").getValue().toString()));
                    shopOrderItemModel.setItemId(dataSnapshot.child("itemId").getValue().toString());
                    shopOrderItemModel.setItemIcon(dataSnapshot.child("itemIcon").getValue().toString());
                    shopOrderItemModel.setItemDesc(dataSnapshot.child("itemDesc").getValue().toString());

                    shopOrderItemModelArrayList.add(shopOrderItemModel);
                }
                ShopOrderDetailsAdapter shopOrderDetailsAdapter = new ShopOrderDetailsAdapter(getContext(),shopOrderItemModelArrayList);
                orderItemsRecycler.setAdapter(shopOrderDetailsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getOrderDetails(){
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("shopOrders").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int orderStatus = Integer.parseInt(snapshot.child("orderStatus").getValue().toString());
                deliveryPersonName = "XYZ";
                deliveryPersonPhoneNumber = "8978117894";
                int orderTotalNumber = Integer.parseInt(snapshot.child("orderTotal").getValue().toString());



                db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child("shopOrders").child(key).child("userAddress").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String address = snapshot.child("address").getValue().toString();
                        deliveryAddress.setText(address);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                AddressModel addressModel;

                orderTotal.setText(String.valueOf(orderTotalNumber));

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
                else if(orderStatus == 104){
                    setupStepperFor104();
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
        details.add("Waiting for shop to accept order");
        details.add("Delivery person assigned");
        details.add("Order picked up");
        details.add("order delivered");

        orderDetails100
                .setStepsViewIndicatorComplectingPosition(details.size()-4)
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
        details.add("Order Accepted");
        details.add("Looking for delivery persons");
        details.add("order pickup");
        details.add("order delivered");

        orderDetails101
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

    private void setupStepperFor102(){
        orderDetails100.setVisibility(View.INVISIBLE);
        orderDetails101.setVisibility(View.INVISIBLE);
        orderDetails102.setVisibility(View.VISIBLE);
        orderDetails103.setVisibility(View.INVISIBLE);
        orderDetails104.setVisibility(View.INVISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order Accepted");
        details.add("Order assigned to " + deliveryPersonName + ","+ deliveryPersonPhoneNumber);
        details.add("Waiting for delivery person to pickup order");
        details.add("order delivered");

        orderDetails102
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

    private void setupStepperFor103(){
        orderDetails100.setVisibility(View.INVISIBLE);
        orderDetails101.setVisibility(View.INVISIBLE);
        orderDetails102.setVisibility(View.INVISIBLE);
        orderDetails103.setVisibility(View.VISIBLE);
        orderDetails104.setVisibility(View.INVISIBLE);

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order Accepted");
        details.add("Order assigned to " + deliveryPersonName + ","+ deliveryPersonPhoneNumber);
        details.add("Order picked up");
        details.add("Waiting for order to be delivered");

        orderDetails103
                .setStepsViewIndicatorComplectingPosition(details.size()-1)
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