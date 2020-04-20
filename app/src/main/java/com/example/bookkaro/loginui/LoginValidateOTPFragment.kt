package com.example.bookkaro.loginui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentLoginValidateOtpBinding

class LoginValidateOTPFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginValidateOtpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_validate_otp, container, false)
        return binding.root
    }
}