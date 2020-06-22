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
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookDeliveryServicesOrderFragmentBinding
import com.example.bookkaro.helper.OrderPackages
import com.example.bookkaro.helper.SendPackages
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

//        val list = mutableListOf<Order>()
//        binding.listOfItemsRecyclerView?.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = listItemsAdapter(list)
//        }

        binding.anyStoreNext.setOnClickListener {
            val category = binding.dropdownText.text.toString().trim()
            val storeAddress=binding.editTextAddress.text.toString().trim()
            val deliveryAddress=binding.editTextDeliveryAddress.text.toString().trim()
            val estimate=binding.editTextEstimatedTotal.text.toString().trim()
            val instructions = binding.editTextInstructions.text.toString().trim()
            val items = binding.listOfItemsEditText.text.toString().trim()

            if(category.isEmpty()){
                binding.dropdownText.error = "Category Required"
                binding.dropdownText.requestFocus()
                return@setOnClickListener
            }
            else if(storeAddress.isEmpty()){
                binding.editTextAddress.error = "Store Address Required"
                binding.editTextAddress.requestFocus()
                return@setOnClickListener
            }
            else if(deliveryAddress.isEmpty()){
                binding.editTextDeliveryAddress.error = "Delivery Address Required"
                binding.editTextDeliveryAddress.requestFocus()
                return@setOnClickListener
            }
            else if(estimate.isEmpty()){
                binding.editTextEstimatedTotal.error = "Your Wishlist is Empty"
                binding.editTextEstimatedTotal.requestFocus()
                return@setOnClickListener
            }
            else if(instructions.isEmpty()){
                binding.editTextInstructions.error = "Instructions Empty"
                binding.editTextInstructions.requestFocus()
                return@setOnClickListener
            }
            else{
                val orderPackages = OrderPackages(category,storeAddress,deliveryAddress,items,estimate,instructions)
                val action = BookDeliveryServicesOrderFragmentDirections.actionBookDeliveryServicesOrderFragmentToPaymentOrderFragment(orderPackages)
                Navigation.findNavController(requireView()).navigate(action)
            }
        }
        return binding.root
    }

}