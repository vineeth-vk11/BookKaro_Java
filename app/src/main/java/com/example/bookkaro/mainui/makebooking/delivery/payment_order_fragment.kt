package com.example.bookkaro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookkaro.databinding.FragmentPaymentOrderFragmentBinding
import com.example.bookkaro.mainui.makebooking.householdservices.PaymentHouseholdFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class payment_order_fragment : Fragment() {

    private lateinit var binding:FragmentPaymentOrderFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val args:payment_order_fragmentArgs by navArgs()
        val orderArgs = args.orderPackages
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_payment_order_fragment,container,false)
        binding.placeOrderButtonOrder.setOnClickListener {
            FirebaseFirestore.getInstance().collection("OrderData").add(
                    hashMapOf(
                            "category" to orderArgs.category,
                            "listOfItems" to orderArgs.items,
                            "storeAddress" to orderArgs.storeAddress,
                            "deliveryAddress" to orderArgs.deliveryAddress,
                            "estimatedTotal" to orderArgs.estimatedTotal,
                            "instructions" to orderArgs.instructions,
                            "status" to 100L,
                            "userId" to FirebaseAuth.getInstance().uid

                    )

            ).addOnSuccessListener {
                val action = payment_order_fragmentDirections.actionPaymentOrderFragmentToOrderPlacedFragment(it.id)
                Navigation.findNavController(requireView()).navigate(action)
            }

        }

        return binding.root
    }


}