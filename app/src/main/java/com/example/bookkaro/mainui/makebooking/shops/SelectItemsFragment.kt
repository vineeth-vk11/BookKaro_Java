package com.example.bookkaro.mainui.makebooking.shops

import android.os.Bundle
import android.util.Log
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
import com.example.bookkaro.helper.shop.*


class SelectItemsFragment : Fragment() {

    private lateinit var binding: FragmentSelectItemsBinding

    private lateinit var viewModel: ShopViewModel
    private lateinit var quantityList: MutableList<ItemQuantity>
    private lateinit var shopUtils: ShopUtils
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_items, container, false)

        val args: SelectItemsFragmentArgs by navArgs()
        val shopId = args.shopId
        shopUtils = ShopUtils(requireContext())
        viewModel = ViewModelProvider(this, ShopViewModelFactory(requireActivity().application)).get(ShopViewModel::class.java)

        viewModel.getShopItems(shopId).observe(viewLifecycleOwner, Observer { items ->
            if (items.isNullOrEmpty())
                setNoItems()
            else {
                setItemsExist()
                binding.itemsRecycler.apply {
                    quantityList = shopUtils.fetchQuantityItems().toMutableList()  //Fetches List from sharedPrefs
                    val decoration = ShopCategoryDecoration(requireContext(), resources.getDimensionPixelSize(R.dimen.header_height), getSectionCallback(items)!!)
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ShopItemAdapter(items, requireContext()) { clickedItem, quantity ->
                        if (quantityList.isNotEmpty()) {
                            if (clickedItem.shopDocId != quantityList[0].shopDocId) { //To check if clicked item is of the same shop as stored in sharedPrefs
                                //To empty the list if you detect an item from another shop
                                quantityList = arrayListOf()
                                //TODO: Alert Dialog
                            }
                        }
                        var newQuantityItem = ItemQuantity(clickedItem.docId, clickedItem.shopDocId, quantity)
                        var flag = true //Just to flag if the item is already present in sharedPrefs list or not
                        quantityList.forEach {
                            if (clickedItem.docId == it.shopItem) {
                                //Update the quantity of the selected item
                                it.quantity = quantity
                                flag = false
                            }
                        }
                        quantityList = quantityList.filter {
                            //To remove those entries whose quantity is 0
                            it.quantity != 0
                        } as MutableList<ItemQuantity>
                        if (flag) {
                            //if item not present in list , add it.
                            quantityList.add(newQuantityItem)
                        }
                        //Push Final result to the sharedPrefs
                        shopUtils.insertQuantityItems(quantityList)
                        Log.d("Quantity", "$quantityList")
                    }
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