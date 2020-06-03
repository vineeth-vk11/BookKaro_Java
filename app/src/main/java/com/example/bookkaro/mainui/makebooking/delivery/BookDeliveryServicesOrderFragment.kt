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
import com.example.bookkaro.helper.bookings.Order
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
        dropDownText=binding.dropdownText

        val items= arrayOf("Stationary")

        val dropDownAdapter:ArrayAdapter<String> = ArrayAdapter(requireContext(),R.layout.list_item,items)
        dropDownText.setAdapter(dropDownAdapter)

        val list = mutableListOf<Order>()
        for (i in 0..2)
        {
            list.add(Order("item 1"))
        }

        binding.listOfItemsRecyclerView?.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter= listItemsAdapter(list)
        }
        return binding.root
    }

}