package com.example.bookkaro.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsHolder> {

    Context context;
    List<Ads> ads;

    public AdsAdapter(Context c, List<Ads> a) {
        context = c;
        ads = a;
    }

    @NonNull
    @Override
    public AdsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdsHolder(LayoutInflater.from(context).inflate(R.layout.ad_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdsHolder holder, int position) {
        Picasso.get().load(ads.get(position).getImage()).fit().into(holder.Ad);
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    class AdsHolder extends RecyclerView.ViewHolder{
        ImageView Ad;
        public AdsHolder(View itemView){
            super(itemView);
            Ad = itemView.findViewById(R.id.AdImage);
        }
    }

}
