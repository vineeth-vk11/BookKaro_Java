package com.example.bookkaro.mainui.makebooking.householdservices.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.SelectServiceFragmentBinding
import com.example.bookkaro.helper.ServicesData
import com.example.bookkaro.helper.home.ServiceOrPackageToBookAdapter
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesFragmentDirections
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesViewModel
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesViewModelFactory
import com.example.bookkaro.mainui.makebooking.householdservices.OrderData

class SelectServiceFragment(private val serviceData: ServicesData) : Fragment() {

    private lateinit var binding: SelectServiceFragmentBinding
    private lateinit var viewModel: BookHouseholdServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.select_service_fragment, container, false)
        viewModel = ViewModelProvider(this, BookHouseholdServicesViewModelFactory(serviceData.docId, requireActivity().application)).get(BookHouseholdServicesViewModel::class.java)

        viewModel.getServices().observe(viewLifecycleOwner, Observer { services ->
            if (services != null) {
                binding.selectServicesRecycler.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ServiceOrPackageToBookAdapter(services, requireContext()) { item, quantity ->
                        viewModel.addToList(item, quantity)
                    }
                }
            }
        })

        viewModel.getPackages().observe(viewLifecycleOwner, Observer { services ->
            if (services != null) {
                binding.selectPackagesRecycler.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ServiceOrPackageToBookAdapter(services, requireContext()) { item, quantity ->
                        viewModel.addToList(item, quantity)
                    }
                }
            }
        })

        binding.continueButton.setOnClickListener {
            if (viewModel.toBook.value != null) {
                if (!viewModel.toBook.value.isNullOrEmpty()) {
                    val action = BookHouseholdServicesFragmentDirections.actionBookHouseholdServicesFragmentToPaymentHouseholdFragment(OrderData(viewModel.toBook.value!!), serviceData.serviceType, serviceData.serviceCategory)
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Must select a service or package first", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Must select a service or package first", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}