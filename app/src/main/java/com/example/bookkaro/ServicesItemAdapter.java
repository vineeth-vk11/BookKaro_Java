package com.example.bookkaro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class ServicesItemAdapter extends RecyclerView.Adapter<ServicesItemAdapter.ServicesViewHolder> {

    private Context context;
    protected List<ServicesData> itemDataList;

    public ServicesItemAdapter(Context context, List<ServicesData> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.service_list_item,parent,false);
        return new ServicesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        holder.service_name.setText(itemDataList.get(position).getName());
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.service_icon);
    }

    @Override
    public int getItemCount() {
        return (itemDataList != null ? itemDataList.size():0);
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder{
        TextView service_name;
        ImageView service_icon;

        public ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.serviceNameHolder);
            service_icon = itemView.findViewById(R.id.serviceIconHolder);

        }
    }
}
