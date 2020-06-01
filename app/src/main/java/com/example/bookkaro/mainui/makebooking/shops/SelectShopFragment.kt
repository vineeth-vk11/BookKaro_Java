package com.example.bookkaro.mainui.makebooking.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.SelectShopFragmentBinding
import com.example.bookkaro.helper.ShopAdapter

class SelectShopFragment : Fragment() {

    private lateinit var binding: SelectShopFragmentBinding

    private lateinit var viewModel: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.select_shop_fragment, container, false)

        val args: SelectShopFragmentArgs by navArgs()
        val shopType = args.shopType

        viewModel = ViewModelProvider(this, ShopViewModelFactory(requireActivity().application)).get(ShopViewModel::class.java)

        viewModel.getShops(shopType).observe(viewLifecycleOwner, Observer { shops ->
            if (!shops.isNullOrEmpty()) {
                setShopsExist()
                binding.shopsRecycler.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ShopAdapter(shops, requireContext(), findNavController())
                }
            } else {
                setNoShops()
            }
        })

        return binding.root
    }

    private fun setNoShops() {
        binding.shopsRecycler.visibility = View.GONE
        binding.noShopImage.visibility = View.VISIBLE
        binding.noShopsText.visibility = View.VISIBLE
    }

    private fun setShopsExist() {
        binding.shopsRecycler.visibility = View.VISIBLE
        binding.noShopImage.visibility = View.GONE
        binding.noShopsText.visibility = View.GONE
    }

}