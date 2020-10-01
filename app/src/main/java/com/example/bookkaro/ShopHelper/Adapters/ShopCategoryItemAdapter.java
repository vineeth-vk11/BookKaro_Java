package com.example.bookkaro.ShopHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookkaro.R;
import com.example.bookkaro.ShopHelper.Models.ShopCartModel;
import com.example.bookkaro.ShopHelper.Models.ShopItemModel;
import com.example.bookkaro.ShopHelper.ViewHolders.ShopItemViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopCategoryItemAdapter extends RecyclerView.Adapter<ShopItemViewHolder> {

    Context context;
    ArrayList<ShopItemModel> shopItemModelArrayList;
    String shopNumber;

    public ShopCategoryItemAdapter(Context context, ArrayList<ShopItemModel> shopItemModelArrayList, String shopNumber) {
        this.context = context;
        this.shopItemModelArrayList = shopItemModelArrayList;
        this.shopNumber = shopNumber;
    }

    @NonNull
    @Override
    public ShopItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_shop_categoroy_item,parent,false);
        return new ShopItemViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemViewHolder holder, int position) {
        String itemName = shopItemModelArrayList.get(position).getItemName();
        String itemPrice = shopItemModelArrayList.get(position).getItemPrice();
        String itemId = String.valueOf(shopItemModelArrayList.get(position).getItemId());
        String itemDesc = shopItemModelArrayList.get(position).getItemDesc();
        String itemIcon = shopItemModelArrayList.get(position).getItemIcon();
        String itemStatus = shopItemModelArrayList.get(position).getItemStatus();

        if(itemStatus.equals("0")){
            holder.addItem.setVisibility(View.INVISIBLE);
            holder.notAvailable.setVisibility(View.VISIBLE);
        }

        holder.itemName.setText(itemName);
        holder.itemPrice.setText(itemPrice);
        holder.itemDesc.setText(shopItemModelArrayList.get(position).getItemDesc());
        Picasso.get().load(shopItemModelArrayList.get(position).getItemIcon()).into(holder.itemIcon);

        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("shopCart").child("shopNumber");
        DatabaseReference databaseReference1 = db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("shopCart").child("items");

        databaseReference1.child(itemName + itemId + itemPrice).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.addItem.setVisibility(View.INVISIBLE);
                    holder.quantity.setVisibility(View.VISIBLE);
                    holder.quantity.setNumber(snapshot.child("itemQuantity").getValue().toString());
                }
                else {
                    if(itemStatus.equals("0")){
                        holder.addItem.setVisibility(View.INVISIBLE);
                        holder.notAvailable.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.quantity.setVisibility(View.INVISIBLE);
                        holder.addItem.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addItem.setVisibility(View.INVISIBLE);
                holder.quantity.setVisibility(View.VISIBLE);

                ShopCartModel shopCartModel = new ShopCartModel();
                shopCartModel.setItemName(itemName);
                shopCartModel.setItemPrice(itemPrice);
                shopCartModel.setItemIcon(itemIcon);
                shopCartModel.setItemDesc(itemDesc);
                shopCartModel.setItemQuantity(0);
                shopCartModel.setItemId(itemId);

                databaseReference.setValue(shopNumber);
                databaseReference1.child(itemName + itemId + itemPrice).setValue(shopCartModel);

            }
        });

        holder.quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                ShopCartModel shopCartModel = new ShopCartModel();
                shopCartModel.setItemName(itemName);
                shopCartModel.setItemPrice(itemPrice);
                shopCartModel.setItemIcon(itemIcon);
                shopCartModel.setItemDesc(itemDesc);
                shopCartModel.setItemQuantity(newValue);
                shopCartModel.setItemId(itemId);

                if(newValue == 0){
                    databaseReference1.child(itemName + itemId + itemPrice).removeValue();
                    holder.quantity.setVisibility(View.INVISIBLE);
                    holder.addItem.setVisibility(View.VISIBLE);
                }
                else {
                    holder.quantity.setVisibility(View.VISIBLE);
                    holder.addItem.setVisibility(View.INVISIBLE);
                    databaseReference1.child(itemName + itemId + itemPrice).setValue(shopCartModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopItemModelArrayList.size();
    }
}
