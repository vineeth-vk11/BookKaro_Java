package com.example.bookkaro.mainui.makebooking.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentShopCartBinding
import com.example.bookkaro.helper.shop.CartAdapter

class ShopCartFragment : Fragment() {

    private lateinit var binding: FragmentShopCartBinding
    private lateinit var viewmodel: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_cart, container, false)
        viewmodel = ViewModelProvider(this, ShopViewModelFactory(requireActivity().application)).get(ShopViewModel::class.java)

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CartAdapter(viewmodel.cartItems)
        }

        binding.proceedToPayButton.setOnClickListener {
            viewmodel.placeOrder()
            findNavController().navigate(R.id.action_shopCartFragment_to_bookingsFragment)
        }

        return binding.root
    }
}