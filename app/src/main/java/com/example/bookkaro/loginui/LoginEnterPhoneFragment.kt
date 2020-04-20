package com.example.bookkaro.loginui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentLoginEnterPhoneBinding

class LoginEnterPhoneFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginEnterPhoneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_enter_phone, container, false)

        binding.loginButton.setOnClickListener {
            val phone = binding.loginPhoneEdit.text.toString()

            //TODO: Validate phone number and then navigate to validate otp fragment
            val action = LoginEnterPhoneFragmentDirections.actionLoginEnterPhoneFragmentToLoginValidateOTPFragment(phone)
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }
}