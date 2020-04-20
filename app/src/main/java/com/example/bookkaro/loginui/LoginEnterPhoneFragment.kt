package com.example.bookkaro.loginui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentLoginEnterPhoneBinding

class LoginEnterPhoneFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginEnterPhoneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_enter_phone, container, false)
        return binding.root
    }
}