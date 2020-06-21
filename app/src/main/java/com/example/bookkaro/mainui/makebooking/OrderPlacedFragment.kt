package com.example.bookkaro.mainui.makebooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookkaro.R
import com.example.bookkaro.databinding.OrderPlacedFragmentBinding

class OrderPlacedFragment : Fragment() {

    private lateinit var binding: OrderPlacedFragmentBinding
    private lateinit var viewModel: OrderPlacedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.order_placed_fragment, container, false)
        val args by navArgs<OrderPlacedFragmentArgs>()
        viewModel = ViewModelProvider(this, OrderPlacedViewModelFactory(args.docId)).get(OrderPlacedViewModel::class.java)

        viewModel.checkOrderStatus().observe(viewLifecycleOwner, Observer {
            binding.orderPlacedSubHeader.text = "Current status: ${getString(viewModel.getOrderStatusString(it))}"
        })

        binding.viewBookingsButton.setOnClickListener { findNavController().navigate(R.id.action_orderPlacedFragment_to_bookingsFragment) }

        return binding.root
    }

}