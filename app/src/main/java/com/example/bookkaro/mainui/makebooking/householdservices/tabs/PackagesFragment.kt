package com.example.bookkaro.mainui.makebooking.householdservices.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.PackagesFragmentBinding
import com.example.bookkaro.helper.ServiceOrPackageToBookAdapter
import com.example.bookkaro.helper.ServicesData
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesViewModel
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesViewModelFactory

class PackagesFragment(private val serviceData: ServicesData) : Fragment() {

    private lateinit var binding: PackagesFragmentBinding

    private lateinit var viewModel: BookHouseholdServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.packages_fragment, container, false)

        viewModel = ViewModelProvider(this, BookHouseholdServicesViewModelFactory(serviceData.docId, requireActivity().application)).get(BookHouseholdServicesViewModel::class.java)

        viewModel.getPackages().observe(viewLifecycleOwner, Observer { services ->
            if (services != null) {
                binding.selectPackagesRecycler.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ServiceOrPackageToBookAdapter(services, requireContext())
                }
            }
        })

        return binding.root
    }

}