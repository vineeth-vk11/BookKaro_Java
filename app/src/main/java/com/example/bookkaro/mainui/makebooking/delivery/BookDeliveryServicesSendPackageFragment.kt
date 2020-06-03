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
import com.example.bookkaro.databinding.BookDeliveryServicesSendPackageFragmentBinding

class BookDeliveryServicesSendPackageFragment : Fragment() {

    private lateinit var binding: BookDeliveryServicesSendPackageFragmentBinding

    private lateinit var viewModel: BookDeliveryServicesSendPackageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_delivery_services_send_package_fragment, container, false)

        val args: BookDeliveryServicesSendPackageFragmentArgs by navArgs()
        val serviceData = args.serviceData

        viewModel = ViewModelProvider(this).get(BookDeliveryServicesSendPackageViewModel::class.java)


        return binding.root
    }

}