package com.example.bookkaro.mainui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.R;
import com.example.bookkaro.helper.Category;
import com.example.bookkaro.helper.CategoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    DatabaseReference reference;
    RecyclerView categoryRecycler;
    private final int STATE_SEE_MORE = 1000;
    CategoryAdapter adapter;
    private final int STATE_SEE_LESS = 1001;
    List<Category> list;
    private int categoriesState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reference = FirebaseDatabase.getInstance().getReference().child("categories");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        reference = FirebaseDatabase.getInstance().getReference().child("Categories");
        Query query = reference.orderByChild("id");
        categoryRecycler = view.findViewById(R.id.categoryRecyclerView);
        final TextView seeAllText = view.findViewById(R.id.see_all_text);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        list = new ArrayList<Category>();
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Category p = dataSnapshot1.getValue(Category.class);
                    list.add(p);
                }
                adapter = new CategoryAdapter(getContext(), list.subList(0, 3));
                categoryRecycler.setAdapter(adapter);

                categoriesState = STATE_SEE_MORE;

                seeAllText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (categoriesState == STATE_SEE_MORE) {
                            categoriesState = STATE_SEE_LESS;
                            seeAllText.setText(R.string.see_less);
                            adapter = new CategoryAdapter(getContext(), list);
                            categoryRecycler.setAdapter(adapter);
                        } else {
                            categoriesState = STATE_SEE_MORE;
                            seeAllText.setText(R.string.see_all);
                            adapter = new CategoryAdapter(getContext(), list.subList(0, 3));
                            categoryRecycler.setAdapter(adapter);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }

}
