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
import com.example.bookkaro.databinding.SelectServiceFragmentBinding
import com.example.bookkaro.helper.ServicesData
import com.example.bookkaro.helper.home.ServiceOrPackageToBookAdapter
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesViewModel
import com.example.bookkaro.mainui.makebooking.householdservices.BookHouseholdServicesViewModelFactory

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
                    adapter = ServiceOrPackageToBookAdapter(services, requireContext())
                }
            }
        })

        return binding.root
    }

}