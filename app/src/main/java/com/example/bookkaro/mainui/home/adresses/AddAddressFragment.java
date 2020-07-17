package com.example.bookkaro.mainui.home.adresses;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bookkaro.R;
import com.example.bookkaro.databinding.AddAddressFragmentBinding;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class AddAddressFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private AddAddressFragmentBinding binding;
    private AddAddressViewModel viewmodel;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private String address;
    private String pincode;

    private Double _latitude;
    private Double _longitude;

    private Long type = 500L;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = AddAddressFragmentBinding.inflate(inflater);
        viewmodel = new ViewModelProvider(this).get(AddAddressViewModel.class);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locate_address_map);
        mapFragment.getMapAsync(this);

        binding.addressTypeChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_chip:
                        type = com.example.bookkaro.helper.home.Address.ADDRESS_HOME;
                        break;
                    case R.id.work_chip:
                        type = com.example.bookkaro.helper.home.Address.ADDRESS_OFFICE;
                        break;
                    case R.id.other_chip:
                        type = com.example.bookkaro.helper.home.Address.ADDRESS_OTHER;
                        break;
                }
            }
        });

        binding.saveAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewmodel.saveAddressToFirestore(address, binding.landmarkEdit.getText().toString(), new GeoPoint(_latitude, _longitude), pincode, type);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addAddressFragment_to_viewAddressFragment);
            }
        });

        return binding.getRoot();
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
            binding.addressText.setText(address);
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
                        binding.addressText.setText(address);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }
}
