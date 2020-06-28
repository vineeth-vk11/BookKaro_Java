package com.example.bookkaro.mainui.home.adresses;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddAddressFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private LatLng pos;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView address;
    private Double Latitude;
    private Double Longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_address_fragment, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.locate_address_map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        pos = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
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
                if (task.isSuccessful() && task.getResult()!=null){
                    Location location = task.getResult();
                    pos = new LatLng(location.getLatitude(), location.getLongitude());
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

                    Latitude = location.getLatitude();
                    Longitude = location.getLongitude();
                    Log.i("latitude is",String.valueOf(location.getLatitude()));
                    Log.i("longitude is",String.valueOf(location.getLongitude()));

                    getAddressFromLocation(Latitude,Longitude);

                }
            }

        });
    }

    private void getAddressFromLocation(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(getContext(),Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);

            if(addresses.size()>0){
                Address fetchedAddress = addresses.get(0);
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i<fetchedAddress.getMaxAddressLineIndex();i++){
                    stringBuilder.append(fetchedAddress.getAddressLine(i)).append(" ");
                }
                Log.i("Location is",stringBuilder.toString());
            }else {
                Log.i("Location is","");
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
