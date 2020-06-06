package com.example.bookkaro.mainui.makebooking.shops

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookDeliveryServicesOrderFragmentBinding
import com.example.bookkaro.databinding.FragmentShopCartBinding
import com.example.bookkaro.helper.home.Order
import com.example.bookkaro.helper.home.listItemsAdapter
import com.example.bookkaro.helper.shop.Cart
import com.example.bookkaro.helper.shop.cartAdapter


class ShopCartFragment : Fragment() {


    private lateinit var binding: FragmentShopCartBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop_cart, container, false)

        val cart = mutableListOf<Cart>()



        binding.cartRecyclerView?.apply {
            layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter= cartAdapter(cart)
        }
        return binding.root

    }


}