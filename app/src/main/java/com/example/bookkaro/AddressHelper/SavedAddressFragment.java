package com.example.bookkaro.AddressHelper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookkaro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SavedAddressFragment extends Fragment {

    CardView addAddress;

    FirebaseUser firebaseUser;
    String uid;
    ArrayList<AddressModel> addressModelArrayList;
    RecyclerView recyclerView;
    FirebaseFirestore db;

    String fragmentTo = "";
    String shopType;
    String shopNumber;
    String shopName;
    String shopIcon;

    String category = "null";
    String estimatedTotal = "null";
    String instructions = "null";

    String serviceName;
    String serviceType;
    String serviceIcon;

    String date;
    String time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_address, container, false);

        Bundle bundle = getArguments();
        if(getArguments() != null){
            fragmentTo = bundle.getString("fragment");
        }

        if(fragmentTo.equals("shopCart")){
            shopType = bundle.getString("shopType");
            shopNumber = bundle.getString("shopNumber");
            shopName = bundle.getString("shopName");
            shopIcon = bundle.getString("shopIcon");
        }

        else if(fragmentTo.equals("orderFromAnywhere")){
            category = bundle.getString("category");
            estimatedTotal = bundle.getString("estimatedTotal");
            instructions = bundle.getString("instructions");
            serviceName = bundle.getString("serviceName");
            serviceIcon = bundle.getString("serviceIcon");
        }

        else if(fragmentTo.equals("household")){
            serviceName = bundle.getString("serviceName");
            serviceIcon = bundle.getString("serviceIcon");
            serviceType = bundle.getString("serviceType");
            time = bundle.getString("time");
            date = bundle.getString("date");
        }

        else if(fragmentTo.equals("sendPackages")){
            serviceName = bundle.getString("serviceName");
            serviceIcon = bundle.getString("serviceIcon");
            instructions = bundle.getString("instructions");
        }

        addAddress = view.findViewById(R.id.add_address_card);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressFragment addAddressFragment = new AddAddressFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,addAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        addressModelArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.addresses_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();

        getSavedAddress();


        return view;
    }

    private void getSavedAddress() {
        db.collection("UserData").document(uid).collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot: task.getResult()){
                    AddressModel addressModel = new AddressModel();
                    addressModel.setAddress(documentSnapshot.getString("address"));
                    addressModel.setAddressType(documentSnapshot.getString("addressType"));
                    addressModel.setPincode(documentSnapshot.getString("pincode"));
                    addressModel.setLongitude(documentSnapshot.getDouble("longitude"));
                    addressModel.setLatitude(documentSnapshot.getDouble("latitude"));
                    addressModel.setApartmentNumber(documentSnapshot.getString("apartmentNumber"));
                    addressModel.setLandMark(documentSnapshot.getString("landMark"));

                    addressModelArrayList.add(addressModel);
                }

                if(fragmentTo.equals("orderFromAnywhere")){
                    SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter(getContext(),addressModelArrayList,fragmentTo,"","","","",category,estimatedTotal,instructions,serviceName,serviceIcon,"","","");
                    recyclerView.setAdapter(savedAddressAdapter);
                }
                else if(fragmentTo.equals("shopCart")){
                    SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter(getContext(),addressModelArrayList,fragmentTo,shopType,shopNumber,shopName,shopIcon,"","","","","","","","");
                    recyclerView.setAdapter(savedAddressAdapter);
                }
                else if(fragmentTo.equals("household")){
                    SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter(getContext(),addressModelArrayList,fragmentTo,"","","","","","","",serviceName,serviceIcon,serviceType,date,time);
                    recyclerView.setAdapter(savedAddressAdapter);
                }
                else if(fragmentTo.equals("sendPackages")){
                    SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter(getContext(),addressModelArrayList,fragmentTo,"","","","","","",instructions,serviceName,serviceIcon,"","","");
                    recyclerView.setAdapter(savedAddressAdapter);
                }
                else if(fragmentTo.equals("home")){
                    SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter(getContext(),addressModelArrayList,fragmentTo,"","","","","","","","","","","","");
                    recyclerView.setAdapter(savedAddressAdapter);
                }
                else {
                    SavedAddressAdapter savedAddressAdapter = new SavedAddressAdapter(getContext(),addressModelArrayList,fragmentTo,"","","","","","","","","","","","");
                    recyclerView.setAdapter(savedAddressAdapter);
                }
            }
        });
    }
}