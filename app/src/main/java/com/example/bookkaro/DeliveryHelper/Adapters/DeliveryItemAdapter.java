package com.example.bookkaro.DeliveryHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookkaro.DeliveryHelper.Models.DeliveryItemModel;
import com.example.bookkaro.DeliveryHelper.ViewHolders.DeliveryItemViewHolder;
import com.example.bookkaro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeliveryItemAdapter extends RecyclerView.Adapter<DeliveryItemViewHolder> {

    Context context;
    ArrayList<DeliveryItemModel> deliveryItemModelArrayList;
    String deliveryType;

    public DeliveryItemAdapter(Context context, ArrayList<DeliveryItemModel> deliveryItemModelArrayList, String deliveryType) {
        this.context = context;
        this.deliveryItemModelArrayList = deliveryItemModelArrayList;
        this.deliveryType = deliveryType;
    }

    @NonNull
    @Override
    public DeliveryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_delivery,parent,false);
        return new DeliveryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryItemViewHolder holder, int position) {
        String key = deliveryItemModelArrayList.get(position).getItemKey();
        holder.itemName.setText(deliveryItemModelArrayList.get(position).getItemName());
        holder.itemQuantity.setNumber(deliveryItemModelArrayList.get(position).getItemQuantity());

        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();


        holder.itemQuantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                DeliveryItemModel deliveryItemModel = new DeliveryItemModel();
                deliveryItemModel.setItemName(deliveryItemModelArrayList.get(position).getItemName());
                deliveryItemModel.setItemQuantity(String.valueOf(newValue));

                DatabaseReference databaseReference = db.getReference().child("sendPackagesCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child(key);

                DatabaseReference databaseReference1 = db.getReference().child("orderFromAnywhereCart").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                        .child(key);

                if(newValue == 0){
                    if(deliveryType.equals("sendPackage")){
                        databaseReference.removeValue();
                    }
                    if(deliveryType.equals("orderFromAnywhere")){
                        databaseReference1.removeValue();
                    }
                    holder.itemQuantity.setVisibility(View.INVISIBLE);
                }
                else {
                    if(deliveryType.equals("sendPackage")){
                        holder.itemQuantity.setVisibility(View.VISIBLE);
                        databaseReference.setValue(deliveryItemModel);
                    }
                    if(deliveryType.equals("orderFromAnywhere")){
                        databaseReference1.setValue(deliveryItemModel);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryItemModelArrayList.size();
    }

}
