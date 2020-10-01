package com.example.bookkaro.HomeUi;

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

import com.example.bookkaro.BookingsHelper.BookingsAdapter;
import com.example.bookkaro.HouseholdHelper.HouseholdCustomerReviewsFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdPackagesFragment;
import com.example.bookkaro.HouseholdHelper.HouseholdServicesFragment;
import com.example.bookkaro.R;
import com.example.bookkaro.helper.AllOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyBookingsFragment extends Fragment {

    FirebaseDatabase db;

    private Toolbar toolbar;

    RecyclerView bookingsRecycler;
    ArrayList<AllOrderModel> allOrderModelArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        toolbar.setTitle("My Bookings");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        allOrderModelArrayList = new ArrayList<>();

        bookingsRecycler = view.findViewById(R.id.bookingsRecycler);
        bookingsRecycler.setHasFixedSize(true);
        bookingsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseDatabase.getInstance();
        db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .child("allOrders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AllOrderModel allOrderModel = new AllOrderModel();
                    allOrderModel.setDateTime(dataSnapshot.child("dateTime").getValue().toString());
                    allOrderModel.setServiceIcon(dataSnapshot.child("serviceIcon").getValue().toString());
                    allOrderModel.setOrderType(Integer.parseInt(dataSnapshot.child("orderType").getValue().toString()));
                    allOrderModel.setKey(dataSnapshot.child("key").getValue().toString());
                    allOrderModel.setServiceName(dataSnapshot.child("serviceName").getValue().toString());

                    allOrderModelArrayList.add(allOrderModel);
                }

                BookingsAdapter bookingsAdapter = new BookingsAdapter(getContext(),allOrderModelArrayList);
                bookingsRecycler.setAdapter(bookingsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}