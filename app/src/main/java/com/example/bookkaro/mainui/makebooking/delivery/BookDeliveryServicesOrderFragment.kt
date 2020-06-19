package com.example.bookkaro.mainui.makebooking.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookDeliveryServicesOrderFragmentBinding
import com.example.bookkaro.helper.home.Order
import com.example.bookkaro.helper.home.listItemsAdapter


class BookDeliveryServicesOrderFragment : Fragment() {
    private lateinit var dropDownText:AutoCompleteTextView
    private lateinit var binding: BookDeliveryServicesOrderFragmentBinding

    private lateinit var viewModel: BookDeliveryServicesOrderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_delivery_services_order_fragment, container, false)

        val args: BookDeliveryServicesOrderFragmentArgs by navArgs()
        val serviceData = args.serviceData

        viewModel = ViewModelProvider(this).get(BookDeliveryServicesOrderViewModel::class.java)

        val items = arrayOf("Stationary", "Item 1", "Item 2")
        val dropDownAdapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        binding.dropdownText.setAdapter(dropDownAdapter)
        binding.dropdownText.setText(items[0], false)

        val list = mutableListOf<Order>()
        binding.listOfItemsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listItemsAdapter(list)
        }
        return binding.root
    }

}