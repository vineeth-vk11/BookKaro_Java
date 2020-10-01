package com.example.bookkaro.HouseholdHelper;

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

import com.example.bookkaro.HouseholdHelper.Adapters.HouseholdServiceAdapter;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdServiceModel;
import com.example.bookkaro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HouseholdServicesFragment extends Fragment {

    FirebaseDatabase db;
    ArrayList<HouseholdServiceModel> householdServiceModelArrayList;
    RecyclerView householdServiceRecycler;

    String serviceId;
    String serviceName;
    String serviceIcon;

    Button continue_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_household_services, container, false);

        Bundle bundle = getArguments();
        serviceId = bundle.getString("serviceId");
        serviceName = bundle.getString("serviceName");
        serviceIcon = bundle.getString("serviceIcon");

        db = FirebaseDatabase.getInstance();
        householdServiceModelArrayList = new ArrayList<>();
        householdServiceRecycler = view.findViewById(R.id.select_services_recycler);

        householdServiceRecycler.setHasFixedSize(true);
        householdServiceRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        householdServiceRecycler.setNestedScrollingEnabled(false);

        db.getReference().child("householdServices").child(String.valueOf(serviceId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getServices();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        continue_button = view.findViewById(R.id.continue_button);
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseholdCartFragment householdCartFragment = new HouseholdCartFragment();

                Bundle bundle1 = new Bundle();
                bundle1.putString("serviceType",serviceId);
                bundle1.putString("serviceName",serviceName);
                bundle1.putString("serviceIcon",serviceIcon);

                householdCartFragment.setArguments(bundle1);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,householdCartFragment);
                fragmentTransaction.commit();
            }
        });

        db.getReference().child("householdCarts").child(serviceId).child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    continue_button.setVisibility(View.VISIBLE);
                }
                else {
                    continue_button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void getServices(){
        db.getReference().child("householdServices").child(serviceId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                householdServiceModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    HouseholdServiceModel householdServiceModel = new HouseholdServiceModel();
                    householdServiceModel.setServiceId(Integer.parseInt(dataSnapshot.child("serviceId").getValue().toString()));
                    householdServiceModel.setServiceName(dataSnapshot.child("serviceName").getValue().toString());
                    householdServiceModel.setServicePrice(Integer.parseInt(dataSnapshot.child("servicePrice").getValue().toString()));

                    Log.i("name",dataSnapshot.child("serviceName").getValue().toString());
                    Log.i("price",dataSnapshot.child("servicePrice").getValue().toString());

                    householdServiceModelArrayList.add(householdServiceModel);
                }

                HouseholdServiceAdapter householdServiceAdapter = new HouseholdServiceAdapter(getContext(),householdServiceModelArrayList,serviceId);
                householdServiceRecycler.setAdapter(householdServiceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}