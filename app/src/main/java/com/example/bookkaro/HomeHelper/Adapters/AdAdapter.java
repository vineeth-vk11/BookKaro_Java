package com.example.bookkaro.HomeHelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.HomeHelper.Models.AdModel;
import com.example.bookkaro.HomeHelper.ViewHolders.AdViewHolder;
import com.example.bookkaro.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdAdapter extends RecyclerView.Adapter<AdViewHolder> {

    Context context;
    ArrayList<AdModel> adModelArrayList;

    public AdAdapter(Context context, ArrayList<AdModel> adModelArrayList) {
        this.context = context;
        this.adModelArrayList = adModelArrayList;
    }


    @NonNull
    @Override
    public AdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_ad,parent,false);
        return new AdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdViewHolder holder, int position) {
        Picasso.get().load(adModelArrayList.get(position).getImage()).into(holder.adImage);
    }

    @Override
    public int getItemCount() {
        return adModelArrayList.size();
    }
}
