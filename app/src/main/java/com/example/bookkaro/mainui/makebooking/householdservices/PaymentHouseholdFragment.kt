package com.example.bookkaro.mainui.makebooking.householdservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookkaro.R
import com.example.bookkaro.databinding.PaymentHouseholdFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class PaymentHouseholdFragment : Fragment() {

    private lateinit var binding: PaymentHouseholdFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.payment_household_fragment, container, false)

        val args by navArgs<PaymentHouseholdFragmentArgs>()
        val orderToPlace = args.orderData

        var serviceNames = ""
        var servicePrices = 0L
        for (service in orderToPlace.data.keys) {
            serviceNames += "${orderToPlace.data[service]}x${service.name}, "
            servicePrices += service.price.times(orderToPlace.data[service] ?: 1)
        }

        binding.placeOrderButton.setOnClickListener {
            FirebaseFirestore.getInstance().collection("OrderData").add(
                    hashMapOf(
                            "acceptedShopNumber" to "",
                            "shopAddress" to "",
                            "shopIconUrl" to "",
                            "shopName" to "",
                            "serviceDate" to Date(),
                            "serviceName" to serviceNames,
                            "servicePrice" to servicePrices,
                            "status" to 100L,
                            "userId" to FirebaseAuth.getInstance().uid,
                            "serviceType" to args.serviceType,
                            "serviceCategory" to args.serviceCategory
                    )
            ).addOnSuccessListener {
                val action = PaymentHouseholdFragmentDirections.actionPaymentHouseholdFragmentToOrderPlacedFragment(it.id)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

}