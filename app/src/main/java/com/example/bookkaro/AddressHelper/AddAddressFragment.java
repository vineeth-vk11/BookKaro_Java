package com.example.bookkaro.AddressHelper;

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

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookkaro.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddAddressFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private String address;
    private String pincode;
    private String apartmentNumber;
    private String landMark;

    private double _latitude;
    private double _longitude;

    TextView addressText;
    Button saveAddressButton;

    EditText houseNumber, landmark;

    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    String uid;

    String addressType;

    ChipGroup addressTypes;

    AddressModel addressModel = new AddressModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);

        addressText = view.findViewById(R.id.address_text);
        saveAddressButton = view.findViewById(R.id.save_address_button);

        addressTypes = view.findViewById(R.id.address_type_chip_group);
        addressTypes.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_chip:
                        addressType = "Home";
                        break;
                    case R.id.work_chip:
                        addressType = "Work";
                        return;
                    case R.id.other_chip:
                        addressType = "Other";
                        return;
                }
            }
        });

        houseNumber = view.findViewById(R.id.house_no_edit);
        landmark = view.findViewById(R.id.landmark_edit);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locate_address_map);
        mapFragment.getMapAsync(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();

        saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddress();
            }
        });
        return view;
    }

    private void saveAddress() {

        if(TextUtils.isEmpty(addressType)){
            Toast.makeText(getContext(),"Please select the address type",Toast.LENGTH_SHORT).show();
        }
        landMark = landmark.getText().toString().trim();
        apartmentNumber = houseNumber.getText().toString().trim();

        addressModel.setAddress(address);
        addressModel.setLandMark(landMark);
        addressModel.setApartmentNumber(apartmentNumber);
        addressModel.setLatitude(_latitude);
        addressModel.setLongitude(_longitude);
        addressModel.setPincode(pincode);
        addressModel.setAddressType(addressType);

        db = FirebaseFirestore.getInstance();
        db.collection("UserData").document(uid).collection("Address").add(addressModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                SavedAddressFragment savedAddressFragment = new SavedAddressFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_nav_host_fragment,savedAddressFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        _latitude = marker.getPosition().latitude;
        _longitude = marker.getPosition().longitude;
        Geocoder geocoder= new Geocoder(getContext());
        try {
            List<Address> matches = geocoder.getFromLocation(_latitude, _longitude, 1);
            Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
            address = bestMatch.getAddressLine(0);
            pincode = bestMatch.getPostalCode();
            addressText.setText(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);
        getLastKnownLocation();
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    _latitude = location.getLatitude();
                    _longitude = location.getLongitude();

                    LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(pos).draggable(true).title("Current location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(17)
                            .bearing(90)
                            .tilt(40)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    Geocoder geocoder= new Geocoder(getContext());
                    try {
                        List<Address> matches = geocoder.getFromLocation(_latitude, _longitude, 1);
                        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                        address = bestMatch.getAddressLine(0);
                        pincode = bestMatch.getPostalCode();
                        addressText.setText(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
}