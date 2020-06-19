package com.example.bookkaro.mainui.makebooking.householdservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookHouseholdServicesFragmentBinding
import com.example.bookkaro.helper.ServicesData
import com.example.bookkaro.mainui.makebooking.householdservices.tabs.CustomerReviewsFragment
import com.example.bookkaro.mainui.makebooking.householdservices.tabs.SelectServiceFragment
import com.google.android.material.tabs.TabLayoutMediator

class BookHouseholdServicesFragment : Fragment() {

    private lateinit var binding: BookHouseholdServicesFragmentBinding

    private val title = listOf("Select services or packages", "Reviews")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_household_services_fragment, container, false)

        val args: BookHouseholdServicesFragmentArgs by navArgs()
        val serviceData = args.serviceData

        binding.bookHouseholdServiceViewpager.adapter = HouseholdServicesAdapter(serviceData)
        TabLayoutMediator(binding.bookHouseholdServiceTab, binding.bookHouseholdServiceViewpager) { tab, position ->
            tab.text = title[position]
        }.attach()

        return binding.root
    }

    private inner class HouseholdServicesAdapter(private val serviceData: ServicesData) : FragmentStateAdapter(requireActivity()) {

        val tabsCount = 2

        override fun getItemCount(): Int {
            return tabsCount
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> SelectServiceFragment(serviceData)
                1 -> CustomerReviewsFragment(serviceData)
                else -> SelectServiceFragment(serviceData)
            }
        }

    }

}
