package com.example.bookkaro.mainui.home.adresses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookkaro.R
import com.example.bookkaro.databinding.ViewAddressFragmentBinding
import com.example.bookkaro.helper.home.AddressAdapter

class ViewAddressFragment : Fragment() {

    private lateinit var viewModel: ViewAddressViewModel
    private lateinit var binding: ViewAddressFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = ViewAddressFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ViewAddressViewModel::class.java)

        binding.addAddressCard.setOnClickListener { findNavController().navigate(R.id.action_viewAddressFragment_to_addAddressFragment) }

        val adapter = AddressAdapter()
        binding.addressesRecycler.adapter = adapter

        viewModel.getAddresses().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }

}