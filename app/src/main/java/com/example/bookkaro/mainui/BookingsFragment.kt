package com.example.bookkaro.mainui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentBookingsBinding
import com.example.bookkaro.helper.BookingsAdapter
import com.example.bookkaro.mainui.viewmodels.BookingsViewModel
import com.example.bookkaro.mainui.viewmodels.BookingsViewModelFactory

class BookingsFragment : Fragment() {

    private lateinit var binding: FragmentBookingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookings, container, false)

        binding.bookingsRecycler.layoutManager = LinearLayoutManager(requireContext())

        val viewModel = ViewModelProvider(this, BookingsViewModelFactory(requireActivity().application)).get(BookingsViewModel::class.java)
        viewModel.getBookings().observe(viewLifecycleOwner, androidx.lifecycle.Observer { bookings ->
            if (bookings.isNotEmpty()) {
                val adapter = BookingsAdapter(bookings, requireContext())
                binding.bookingsRecycler.adapter = adapter
            }
        })

        return binding.root
    }

}