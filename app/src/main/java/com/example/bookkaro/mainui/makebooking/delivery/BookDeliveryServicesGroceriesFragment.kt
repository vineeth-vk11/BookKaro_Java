package com.example.bookkaro.mainui.makebooking.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookDeliveryServicesGroceriesFragmentBinding

class BookDeliveryServicesGroceriesFragment : Fragment() {

    private lateinit var binding: BookDeliveryServicesGroceriesFragmentBinding

    private lateinit var viewModel: BookDeliveryServicesGroceriesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_delivery_services_groceries_fragment, container, false)

        val args: BookDeliveryServicesGroceriesFragmentArgs by navArgs()
        val serviceData = args.serviceData

        viewModel = ViewModelProvider(this).get(BookDeliveryServicesGroceriesViewModel::class.java)

        binding.textView.text = "Fragment: BookDeliveryServicesGroceriesFragment\nDocument ID: ${serviceData.docId}\nService Requested: ${serviceData.name}"

        return binding.root
    }

}