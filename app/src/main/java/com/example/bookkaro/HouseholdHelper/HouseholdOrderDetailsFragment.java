package com.example.bookkaro.HouseholdHelper;

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
import com.baoyachi.stepview.bean.StepBean;
import com.example.bookkaro.HomeUi.MyBookingsFragment;
import com.example.bookkaro.HouseholdHelper.Adapters.HouseholdOrderDetailsAdapter;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdCartModel;
import com.example.bookkaro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class HouseholdOrderDetailsFragment extends Fragment {
    VerticalStepView orderDetails100;
    VerticalStepView orderDetails101;
    VerticalStepView orderDetails102;
    VerticalStepView orderDetails103;

    FirebaseDatabase db;

    String key;

    String vendorName;
    String vendorPhoneNumber;

    private Toolbar toolbar;

    ArrayList<HouseholdCartModel> householdCartModelArrayList;

    RecyclerView orderDetailsRecycler;

    TextView deliveryAddress;

    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_household_order_details, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        next = view.findViewById(R.id.nextButton);

        toolbar.setTitle("Order Details");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        Bundle bundle = getArguments();
        key = bundle.getString("key");

        deliveryAddress = view.findViewById(R.id.deliveryAddress);
        orderDetails100 = view.findViewById(R.id.orderStatus100);
        orderDetails101 = view.findViewById(R.id.orderStatus101);
        orderDetails102 = view.findViewById(R.id.orderStatus102);
        orderDetails103 = view.findViewById(R.id.orderStatus103);

        householdCartModelArrayList = new ArrayList<>();

        orderDetailsRecycler = view.findViewById(R.id.orderDetailsRecycler);
        orderDetailsRecycler.setNestedScrollingEnabled(false);
        orderDetailsRecycler.setHasFixedSize(true);
        orderDetailsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseDatabase.getInstance();

        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("placedOrders").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getOrderDetails();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void getOrderDetails(){

        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("householdOrders").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int orderStatus = Integer.parseInt(snapshot.child("orderStatus").getValue().toString());
                vendorName = snapshot.child("vendorName").getValue().toString();
                vendorPhoneNumber = snapshot.child("vendorPhoneNumber").getValue().toString();
                deliveryAddress.setText(snapshot.child("deliveryAddress").getValue().toString());

                db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child("householdOrders").child(key).child("orderItems").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            HouseholdCartModel householdCartModel = new HouseholdCartModel();
                            householdCartModel.setServiceName(dataSnapshot.child("serviceName").getValue().toString());
                            householdCartModel.setServicePrice(Integer.parseInt(dataSnapshot.child("servicePrice").getValue().toString()));
                            householdCartModel.setServiceId(Integer.parseInt(dataSnapshot.child("serviceId").getValue().toString()));
                            householdCartModel.setServiceQuantity(Integer.parseInt(dataSnapshot.child("serviceQuantity").getValue().toString()));

                            householdCartModelArrayList.add(householdCartModel);
                        }
                        HouseholdOrderDetailsAdapter householdOrderDetailsAdapter = new HouseholdOrderDetailsAdapter(getContext(), householdCartModelArrayList);
                        orderDetailsRecycler.setAdapter(householdOrderDetailsAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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

        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Looking for vendors to accept order");
        details.add("Service Started");
        details.add("Service Completed");

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
        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order accepted by vendor " + vendorName + ","+ vendorPhoneNumber);
        details.add("Waiting for vendor to start service");
        details.add("Service Completed");

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
        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order accepted by vendor " + vendorName + ","+ vendorPhoneNumber);
        details.add("Service Started");
        details.add("Waiting for vendor to complete service");

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
        List<String> details = new ArrayList<>();
        details.clear();

        details.add("Order Placed");
        details.add("Order accepted by vendor " + vendorName + ","+ vendorPhoneNumber);
        details.add("Service Started");
        details.add("Service Completed");

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

}