package com.example.bookkaro.mainui.home.adresses

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.R
import com.example.bookkaro.databinding.AddAddressFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class AddAddressFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private lateinit var binding: AddAddressFragmentBinding
    private lateinit var viewModel: AddAddressViewModel

    private lateinit var _map: GoogleMap
    private lateinit var _fusedLocationProviderClient: FusedLocationProviderClient

    private var permissionDenied = false
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = AddAddressFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(AddAddressViewModel::class.java)

        val mapFragment = childFragmentManager.findFragmentById(R.id.locate_address_map) as SupportMapFragment
        _fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _map = googleMap

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        enableMyLocation()
    }

    private fun enableMyLocation() {
        if (!::_map.isInitialized) return

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            _map.isMyLocationEnabled = true
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        // Return false so that the default behavior occurs (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        // Don't do anything so that the default behavior occurs.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_LOCATION_PERMISSION)
            return
        else {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            } else {
                permissionDenied = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            Toast.makeText(requireContext(), "We won't be able to fetch your location without location permissions", Toast.LENGTH_SHORT).show()
            permissionDenied = false
        }
    }
}