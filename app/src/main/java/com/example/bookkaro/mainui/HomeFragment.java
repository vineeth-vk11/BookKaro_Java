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
import androidx.recyclerview.widget.RecyclerView;

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
    RecyclerView categoryRecycler;
    ArrayList<Category> list;
    CategoryAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reference = FirebaseDatabase.getInstance().getReference().child("categories");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        reference = FirebaseDatabase.getInstance().getReference().child("Categories");
        Query query = reference.orderByChild("id");
        categoryRecycler = view.findViewById(R.id.categoryRecyclerView);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        list = new ArrayList<Category>();

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


        return view;

    }
}
