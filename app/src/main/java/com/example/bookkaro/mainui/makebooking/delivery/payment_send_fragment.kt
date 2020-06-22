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
import com.example.bookkaro.databinding.FragmentPaymentSendFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class payment_send_fragment : Fragment() {

    private lateinit var binding: FragmentPaymentSendFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val args : payment_send_fragmentArgs by navArgs()
        val sendArgs=args.sendPackages
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_payment_send_fragment,container,false)
        binding.placeOrderButtonSend.setOnClickListener {
            FirebaseFirestore.getInstance().collection("OrderData").add(
                    hashMapOf(
                            "pickupAddress" to sendArgs?.pickupAddress,
                            "deliveryAddress" to sendArgs?.deliveryAddress,
                            "packageContents" to sendArgs?.packageContents,
                            "instructions" to sendArgs?.instructions,
                            "status" to 100L,
                            "userId" to FirebaseAuth.getInstance().uid
                             )
            ).addOnSuccessListener {
                val action =payment_send_fragmentDirections.actionPaymentSendFragmentToOrderPlacedFragment(it.id)
                Navigation.findNavController(requireView()).navigate(action)
            }
        }


        return binding.root
    }


}