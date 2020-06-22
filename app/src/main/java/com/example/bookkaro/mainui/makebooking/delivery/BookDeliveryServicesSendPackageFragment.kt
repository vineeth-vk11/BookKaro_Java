package com.example.bookkaro.mainui.makebooking.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.bookkaro.R
import com.example.bookkaro.databinding.BookDeliveryServicesSendPackageFragmentBinding
import com.example.bookkaro.helper.SendPackages

class BookDeliveryServicesSendPackageFragment : Fragment() {

    private lateinit var binding: BookDeliveryServicesSendPackageFragmentBinding

    private lateinit var viewModel: BookDeliveryServicesSendPackageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.book_delivery_services_send_package_fragment, container, false)

        val args: BookDeliveryServicesSendPackageFragmentArgs by navArgs()
        val serviceData = args.serviceData

        viewModel = ViewModelProvider(this).get(BookDeliveryServicesSendPackageViewModel::class.java)

        binding.sendPackageNext.setOnClickListener {
            val pickupAddress=binding.editTextPickupAddress.text.toString().trim()
            val deliveryAddress=binding.SENDEditTextDeliveryAddress.text.toString().trim()
            val packageContents=binding.editTextPackage.text.toString().trim()
            val instructions=binding.SENDEditTextInstructions.text.toString().trim()
            if(pickupAddress.isEmpty()){
                binding.editTextPickupAddress.error = "Pickup Address Required"
                binding.editTextPickupAddress.requestFocus()
                return@setOnClickListener
            }
            else if(deliveryAddress.isEmpty()){
                binding.SENDEditTextDeliveryAddress.error = "Delivery Address Required"
                binding.SENDEditTextDeliveryAddress.requestFocus()
                return@setOnClickListener
            }
            else if(packageContents.isEmpty()){
                binding.editTextPackage.error = "Package Contents Required"
                binding.editTextPackage.requestFocus()
                return@setOnClickListener
            }
            else if(instructions.isEmpty()){
                binding.SENDEditTextInstructions.error = "Instructions Required"
                binding.SENDEditTextInstructions.requestFocus()
                return@setOnClickListener
            }
            else{
                val sendPackages = SendPackages(pickupAddress,deliveryAddress,packageContents,instructions)
                val action = BookDeliveryServicesSendPackageFragmentDirections.actionBookDeliveryServicesSendPackageFragmentToPaymentSendFragment(sendPackages)
                Navigation.findNavController(requireView()).navigate(action)
            }
        }


        return binding.root
    }

}