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
import com.example.bookkaro.ShopHelper.ViewHolders.ShopCartViewHolder;
import com.example.bookkaro.ShopHelper.ViewHolders.ShopItemViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartViewHolder> {

    Context context;
    ArrayList<ShopCartModel> shopCartModelArrayList;

    public ShopCartAdapter(Context context, ArrayList<ShopCartModel> shopCartModelArrayList) {
        this.context = context;
        this.shopCartModelArrayList = shopCartModelArrayList;
    }

    @NonNull
    @Override
    public ShopCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_shop_cart,parent, false);
        return new ShopCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCartViewHolder holder, int position) {

        String itemName = shopCartModelArrayList.get(position).getItemName();
        String itemPrice = shopCartModelArrayList.get(position).getItemPrice();
        String itemQuantity = String.valueOf(shopCartModelArrayList.get(position).getItemQuantity());
        String itemIcon = shopCartModelArrayList.get(position).getItemIcon();
        String itemDesc = shopCartModelArrayList.get(position).getItemDesc();
        String itemId = String.valueOf(shopCartModelArrayList.get(position).getItemId());

        holder.itemName.setText(itemName);
        holder.itemPrice.setText(itemPrice);
        holder.itemDesc.setText(itemDesc);
        Picasso.get().load(itemIcon).into(holder.itemIcon);
        holder.quantity.setNumber(itemQuantity);

        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = db.getReference().child("userData").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("shopCart").child("items");

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
                }
                else {
                    holder.quantity.setVisibility(View.VISIBLE);
                    databaseReference1.child(itemName + itemId + itemPrice).setValue(shopCartModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopCartModelArrayList.size();
    }
}
