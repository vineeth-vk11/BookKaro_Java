package com.example.bookkaro.mainui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.Ads;
import com.example.bookkaro.AdsAdapter;
import com.example.bookkaro.Category;
import com.example.bookkaro.CategoryAdapter;
import com.example.bookkaro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    DatabaseReference reference;
    DatabaseReference reference1;
    RecyclerView categoryRecycler;
    RecyclerView adsRecycler;
    ArrayList<Category> list;
    ArrayList<Ads>list1;
    CategoryAdapter adapter;
    AdsAdapter adapter1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reference = FirebaseDatabase.getInstance().getReference().child("categories");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        reference = FirebaseDatabase.getInstance().getReference().child("Categories");
        reference1 = FirebaseDatabase.getInstance().getReference().child("Ads");
        categoryRecycler = view.findViewById(R.id.categoryRecyclerView);
        adsRecycler = view.findViewById(R.id.AdRecycler);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        adsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        list = new ArrayList<Category>();
        list1 = new ArrayList<Ads>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Category p = dataSnapshot1.getValue(Category.class);
                    list.add(p);
                }
                adapter = new CategoryAdapter(getContext(),list);
                categoryRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    Ads p = dataSnapshot1.getValue(Ads.class);
                    list1.add(p);
                }
                adapter1 = new AdsAdapter(getContext(),list1);
                adsRecycler.setAdapter(adapter1);
                Toast.makeText(getContext(),"Successful",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }
}
