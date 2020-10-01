package com.example.bookkaro.HomeUi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.bookkaro.AddressHelper.AddressModel;
import com.example.bookkaro.AddressHelper.SavedAddressFragment;
import com.example.bookkaro.HomeHelper.Adapters.AdAdapter;
import com.example.bookkaro.HomeHelper.Adapters.AllCategoriesAdapter;
import com.example.bookkaro.HomeHelper.Adapters.CategoryAdapter;
import com.example.bookkaro.HomeHelper.Models.AdModel;
import com.example.bookkaro.HomeHelper.Models.AllCategoriesModel;
import com.example.bookkaro.HomeHelper.Models.CategoryModel;
import com.example.bookkaro.HomeHelper.Models.ServiceModel;
import com.example.bookkaro.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FirebaseDatabase db;
    ArrayList<CategoryModel> categoryModelArrayList;
    ArrayList<AdModel> adModelArrayList;
    ArrayList<AllCategoriesModel> allCategoriesModelArrayList;

    RecyclerView categoryRecycler;
    RecyclerView adRecycler;
    RecyclerView allCategoriesRecycler;


    private FusedLocationProviderClient fusedLocationProviderClient;
    private String pincode;
    private double _latitude;
    private double _longitude;
    private String address;
    private String locality;
    private String subLocality;

    Button addressButton;

    AddressModel addressModel = new AddressModel();

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addressButton = view.findViewById(R.id.current_address_text);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        progressBar = view.findViewById(R.id.progressBar);

        Bundle bundle = getArguments();
        if(getArguments() != null){
            pincode = bundle.getString("pincode");
            addressButton.setText(bundle.getString("pincode"));
        }
        else if(getArguments() == null){
            fetchLocation();
        }

        categoryModelArrayList = new ArrayList<>();
        adModelArrayList = new ArrayList<>();
        allCategoriesModelArrayList = new ArrayList<>();

        categoryRecycler = view.findViewById(R.id.categoryRecyclerView);
        categoryRecycler.setHasFixedSize(true);
        categoryRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        categoryRecycler.setNestedScrollingEnabled(false);

        adRecycler = view.findViewById(R.id.AdRecycler);
        adRecycler.setHasFixedSize(true);
        adRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        allCategoriesRecycler = view.findViewById(R.id.allCategoriesRecycler);
        allCategoriesRecycler.setHasFixedSize(true);
        allCategoriesRecycler.setNestedScrollingEnabled(false);
        allCategoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseDatabase.getInstance();
        db.getReference().child("categories").orderByChild("categoryId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCategories();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.getReference().child("Ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getAds();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.getReference().child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getServices();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedAddressFragment savedAddressFragment = new SavedAddressFragment();

                Bundle bundle = new Bundle();
                bundle.putString("fragment","home");

                savedAddressFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,savedAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else {
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        location = task.getResult();
                        _latitude = location.getLatitude();
                        _longitude = location.getLongitude();

                        Geocoder geocoder = new Geocoder(getContext());
                        try {
                            List<Address> matches = geocoder.getFromLocation(_latitude, _longitude, 1);
                            Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                            address = bestMatch.getAddressLine(0);
                            pincode = bestMatch.getPostalCode();
                            locality = bestMatch.getLocality();
                            subLocality = bestMatch.getSubLocality();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        addressButton.setText(pincode);
                        Log.i("Latitude is",String.valueOf(_latitude));
                        Log.i("Longitude is",String.valueOf(_longitude));
                        Log.i("Addess",address);
                        Log.i("pincode",pincode);
                        Log.i("Locality",locality);
                        Log.i("subLocality",subLocality);
                    }
                }
            });
        }
    }

    private void getCategories(){
        db.getReference().child("categories").orderByChild("categoryId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                categoryModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setCategoryName(dataSnapshot.child("categoryName").getValue().toString());
                    categoryModel.setCategoryIcon(dataSnapshot.child("categoryIcon").getValue().toString());
                    categoryModel.setCategoryId(Integer.parseInt(dataSnapshot.child("categoryId").getValue().toString()));

                    categoryModelArrayList.add(categoryModel);
                }

                CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(),categoryModelArrayList);
                categoryRecycler.setAdapter(categoryAdapter);
                progressBar.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAds(){

        db.getReference().child("Ads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                adModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AdModel adModel = new AdModel();
                    adModel.setImage(dataSnapshot.child("image").getValue().toString());

                    adModelArrayList.add(adModel);
                }

                AdAdapter adAdapter = new AdAdapter(getContext(),adModelArrayList);
                adRecycler.setAdapter(adAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getServices(){
        db.getReference().child("services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCategoriesModelArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AllCategoriesModel allCategoriesModel = new AllCategoriesModel();
                    allCategoriesModel.setCategoryName(dataSnapshot.child("categoryName").getValue().toString());
                    allCategoriesModel.setCategoryId(Integer.parseInt(dataSnapshot.child("categoryId").getValue().toString()));

                    GenericTypeIndicator<ArrayList<ServiceModel>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ServiceModel>>() {};
                    allCategoriesModel.setServiceModelArrayList(dataSnapshot.child("serviceItems").getValue(genericTypeIndicator));

                    allCategoriesModelArrayList.add(allCategoriesModel);
                }

                AllCategoriesAdapter allCategoriesAdapter = new AllCategoriesAdapter(getContext(),allCategoriesModelArrayList,pincode);
                allCategoriesRecycler.setAdapter(allCategoriesAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}