package com.example.bookkaro.mainui.makebooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookHouseholdServicesFragmentBinding

class BookHouseholdServicesFragment : Fragment() {

    private lateinit var binding: BookHouseholdServicesFragmentBinding

    private lateinit var viewModel: BookHouseholdServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_household_services_fragment, container, false)

        val args: BookHouseholdServicesFragmentArgs by navArgs()
        val serviceData = args.serviceData

        viewModel = ViewModelProvider(this).get(BookHouseholdServicesViewModel::class.java)


        binding.textView.text = "Fragment: BookHouseholdServicesFragment\nDocument ID: ${serviceData.docId}\nService Requested: ${serviceData.name}"


        return binding.root
    }

}
