package com.example.bookkaro.mainui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkaro.Interface.iFirebaseLoadListener;
import com.example.bookkaro.R;
import com.example.bookkaro.helper.Ads;
import com.example.bookkaro.helper.AdsAdapter;
import com.example.bookkaro.helper.Category;
import com.example.bookkaro.helper.CategoryAdapter;
import com.example.bookkaro.helper.ServicesData;
import com.example.bookkaro.helper.ServicesGroup;
import com.example.bookkaro.helper.ServicesGroupAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment implements com.example.bookkaro.Interface.iFirebaseLoadListener {

    AlertDialog dialog;
    iFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView recyclerView;
    DatabaseReference myData;

    DatabaseReference reference;
    DatabaseReference reference1;
    RecyclerView categoryRecycler;
    private final int STATE_SEE_MORE = 1000;
    RecyclerView adsRecycler;
    ArrayList<Ads>list1;
    CategoryAdapter adapter;
    private final int STATE_SEE_LESS = 1001;
    List<Category> list;
    private int categoriesState;

    AdsAdapter adapter1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reference = FirebaseDatabase.getInstance().getReference().child("categories");
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        reference = FirebaseDatabase.getInstance().getReference().child("Categories");
        reference1 = FirebaseDatabase.getInstance().getReference().child("Ads");
        myData = FirebaseDatabase.getInstance().getReference("ServicesData");


        categoryRecycler = view.findViewById(R.id.categoryRecyclerView);
        final TextView seeAllText = view.findViewById(R.id.see_all_text);

        adsRecycler = view.findViewById(R.id.AdRecycler);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        adsRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        dialog = new SpotsDialog.Builder().setContext(getContext()).build();
        iFirebaseLoadListener = this;
        recyclerView = view.findViewById(R.id.servicesRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getFirebaseData();


        list = new ArrayList<Category>();
        list1 = new ArrayList<Ads>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

    private void getFirebaseData() {
        dialog.show();
        myData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ServicesGroup> servicesGroups = new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ServicesGroup servicesGroup = new ServicesGroup();
                    servicesGroup.setHeaderTitle(dataSnapshot1.child("headerTitle").getValue(true).toString());
                    GenericTypeIndicator<ArrayList<ServicesData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ServicesData>>(){};
                    servicesGroup.setListItem(dataSnapshot1.child("listItem").getValue(genericTypeIndicator));
                    servicesGroups.add(servicesGroup);
                }
                iFirebaseLoadListener.onFirebaseLoadSuccess(servicesGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<ServicesGroup> servicesGroupList) {
        ServicesGroupAdapter adapter = new ServicesGroupAdapter(getContext(),servicesGroupList);
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }
}
