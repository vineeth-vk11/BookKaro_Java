package com.example.bookkaro.HouseholdHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdCartModel;
import com.example.bookkaro.HouseholdHelper.Models.HouseholdServiceModel;
import com.example.bookkaro.HouseholdHelper.ViewHolders.HouseholdServiceViewHolder;
import com.example.bookkaro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HouseholdServiceAdapter extends RecyclerView.Adapter<HouseholdServiceViewHolder> {

    Context context;
    ArrayList<HouseholdServiceModel> householdServiceModelArrayList;
    String serviceType;

    public HouseholdServiceAdapter(Context context, ArrayList<HouseholdServiceModel> householdServiceModelArrayList, String serviceType) {
        this.context = context;
        this.householdServiceModelArrayList = householdServiceModelArrayList;
        this.serviceType = serviceType;
    }

    @NonNull
    @Override
    public HouseholdServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_household_service,parent,false);
        return new HouseholdServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseholdServiceViewHolder holder, int position) {

        String serviceName = householdServiceModelArrayList.get(position).getServiceName();
        int servicePrice = householdServiceModelArrayList.get(position).getServicePrice();
        int serviceId = householdServiceModelArrayList.get(position).getServiceId();

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("householdCarts").child(serviceType)
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("householdCarts").child(serviceType)
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child(String.valueOf(serviceId));

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.addItem.setVisibility(View.INVISIBLE);
                    holder.quantityChange.setVisibility(View.VISIBLE);
                    holder.quantityChange.setNumber(String.valueOf(snapshot.child("serviceQuantity").getValue().toString()));
                }
                else {
                    holder.quantityChange.setVisibility(View.INVISIBLE);
                    holder.addItem.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.serviceName.setText(householdServiceModelArrayList.get(position).getServiceName());
        holder.servicePrice.setText(String.valueOf(servicePrice));

        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addItem.setVisibility(View.INVISIBLE);
                holder.quantityChange.setVisibility(View.VISIBLE);

                HouseholdCartModel householdCartModel = new HouseholdCartModel();
                householdCartModel.setServiceName(serviceName);
                householdCartModel.setServicePrice(servicePrice);
                householdCartModel.setServiceId(serviceId);
                householdCartModel.setServiceQuantity(0);

                databaseReference1.child(String.valueOf(serviceId)).setValue(householdCartModel);
            }
        });

        holder.quantityChange.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                HouseholdCartModel householdCartModel = new HouseholdCartModel();
                householdCartModel.setServiceName(serviceName);
                householdCartModel.setServiceId(serviceId);
                householdCartModel.setServicePrice(servicePrice);
                householdCartModel.setServiceQuantity(newValue);

                if(newValue == 0){
                    databaseReference1.child(String.valueOf(serviceId)).removeValue();
                    holder.quantityChange.setVisibility(View.INVISIBLE);
                    holder.addItem.setVisibility(View.VISIBLE);
                }
                else {
                    holder.quantityChange.setVisibility(View.VISIBLE);
                    holder.addItem.setVisibility(View.INVISIBLE);
                    databaseReference1.child(String.valueOf(serviceId)).setValue(householdCartModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return householdServiceModelArrayList.size();
    }
}
