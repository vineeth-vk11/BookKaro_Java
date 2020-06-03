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
import com.example.bookkaro.helper.shop.ShopCategoryDecoration
import com.example.bookkaro.helper.shop.ShopItem
import com.example.bookkaro.helper.shop.ShopItemAdapter


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
                    val decoration = ShopCategoryDecoration(requireContext(), resources.getDimensionPixelSize(R.dimen.header_height), getSectionCallback(items)!!)
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ShopItemAdapter(items, requireContext())
                    addItemDecoration(decoration)
                }
            }
        })

        return binding.root
    }

    private fun getSectionCallback(list: List<Map<String, ShopItem>>): ShopCategoryDecoration.SectionCallback? {
        return object : ShopCategoryDecoration.SectionCallback {
            override fun isSectionHeader(pos: Int): Boolean {
                return pos == 0 || list[pos].keys.elementAt(0) != list[pos - 1].keys.elementAt(0)
            }

            override fun getSectionHeaderName(pos: Int): String {
                return list[pos].keys.elementAt(0)
            }
        }
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