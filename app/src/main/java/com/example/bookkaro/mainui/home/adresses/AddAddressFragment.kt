package com.example.bookkaro.mainui.home.adresses

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bookkaro.databinding.AddAddressFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AddAddressFragment : Fragment() {

    private lateinit var binding: AddAddressFragmentBinding
    private lateinit var viewModel: AddAddressViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location: Location? = null
    private val REQUEST_LOCATION_PERMISSION = 1

    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = AddAddressFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(AddAddressViewModel::class.java)

        if (locationPermissionIsGranted()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    this.location = location
                    with(binding.locateAddressMap) {
                        onCreate(null)
                        getMapAsync {
                            com.google.android.gms.maps.MapsInitializer.initialize(context)
                            setMapLocation(it, LatLng(location.latitude, location.longitude))
                        }
                    }
                }
            }
        } else {
            requestLocationPermission()
            binding.locateAddressMap.visibility = View.GONE
        }

        return binding.root
    }

    private fun setMapLocation(map: GoogleMap, location: LatLng) {
        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
            addMarker(MarkerOptions().position(location))
            mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }

    private fun locationPermissionIsGranted() = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() = ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)

    override fun onResume() {
        super.onResume()
        binding.locateAddressMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.locateAddressMap.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.locateAddressMap.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.locateAddressMap.onLowMemory()
    }

}