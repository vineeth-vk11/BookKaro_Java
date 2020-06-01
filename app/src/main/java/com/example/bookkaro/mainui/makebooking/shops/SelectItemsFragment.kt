package com.example.bookkaro.mainui.makebooking.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentSelectItemsBinding
import com.example.bookkaro.helper.ShopItemAdapter


class SelectItemsFragment : Fragment() {

    private lateinit var binding: FragmentSelectItemsBinding

    private lateinit var viewModel: ShopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_items, container, false)

        val args: SelectItemsFragmentArgs by navArgs()
        val shopId = args.shopId

        viewModel = ViewModelProvider(this, ShopViewModelFactory(requireActivity().application)).get(ShopViewModel::class.java)

        viewModel.getShopItems(shopId).observe(viewLifecycleOwner, Observer { items ->
            if (items.isNullOrEmpty())
                setNoItems()
            else {
                setItemsExist()
                binding.itemsRecycler.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ShopItemAdapter(items, requireContext())
                }
            }
        })

        return binding.root
    }

    private fun setNoItems() {
        binding.itemsRecycler.visibility = View.GONE
        binding.noItemsImage.visibility = View.VISIBLE
        binding.noItemsText.visibility = View.VISIBLE
    }

    private fun setItemsExist() {
        binding.itemsRecycler.visibility = View.VISIBLE
        binding.noItemsImage.visibility = View.GONE
        binding.noItemsText.visibility = View.GONE
    }

}