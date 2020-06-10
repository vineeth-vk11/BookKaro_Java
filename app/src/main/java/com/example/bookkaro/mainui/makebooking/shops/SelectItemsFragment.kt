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
import com.example.bookkaro.databinding.FragmentSelectItemsBinding
import com.example.bookkaro.helper.shop.*


class SelectItemsFragment : Fragment() {

    private lateinit var binding: FragmentSelectItemsBinding

    private lateinit var viewModel: ShopViewModel
    private lateinit var listCart: MutableList<CartItem>
    private lateinit var shopUtils: ShopUtils
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_items, container, false)

        val args: SelectItemsFragmentArgs by navArgs()
        val shopId = args.shopId
        shopUtils = ShopUtils(requireActivity().application)
        viewModel = ViewModelProvider(this, ShopViewModelFactory(requireActivity().application)).get(ShopViewModel::class.java)

        viewModel.getShopItems(shopId).observe(viewLifecycleOwner, Observer { items ->
            if (items.isNullOrEmpty())
                setNoItems()
            else {
                setItemsExist()
                binding.itemsRecycler.apply {
                    listCart = shopUtils.fetchQuantityItems().toMutableList()  //Fetches List from sharedPrefs
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ShopItemAdapter(items, requireActivity().application, context) { clickedItem, quantity ->
                        if (listCart.isNotEmpty()) {
                            if (clickedItem.shopDocId != listCart[0].shopDocId) { //To check if clicked item is of the same shop as stored in sharedPrefs
                                //To empty the list if you detect an item from another shop
                                listCart = arrayListOf()
                                //TODO: Alert Dialog
                            }
                        }
                        var newQuantityItem = CartItem(clickedItem.docId, clickedItem.shopDocId, clickedItem.name, clickedItem.price, quantity)
                        var flag = true //Just to flag if the item is already present in sharedPrefs list or not
                        listCart.forEach {
                            if (clickedItem.docId == it.shopItemId) {
                                //Update the quantity of the selected item
                                it.quantity = quantity
                                flag = false
                            }
                        }
                        listCart = listCart.filter {
                            //To remove those entries whose quantity is 0
                            it.quantity != 0
                        } as MutableList<CartItem>
                        if (flag) {
                            //if item not present in list , add it.
                            listCart.add(newQuantityItem)
                        }
                        //Push Final result to the sharedPrefs
                        shopUtils.insertQuantityItems(listCart)

                    }
                    if (itemDecorationCount == 0) {
                        val decoration = ShopCategoryDecoration(requireContext(), resources.getDimensionPixelSize(R.dimen.header_height), getSectionCallback(items)!!)
                        addItemDecoration(decoration)
                    }
                }
            }
        })

        binding.cartImage.setOnClickListener { findNavController().navigate(R.id.action_selectItemsFragment_to_shopCartFragment) }

        return binding.root
    }

    private fun getSectionCallback(list: List<ShopItem>): ShopCategoryDecoration.SectionCallback? {
        return object : ShopCategoryDecoration.SectionCallback {
            override fun isSectionHeader(pos: Int): Boolean {
                return pos == 0 || list[pos].category != list[pos - 1].category
            }

            override fun getSectionHeaderName(pos: Int): String {
                return list[pos].category
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