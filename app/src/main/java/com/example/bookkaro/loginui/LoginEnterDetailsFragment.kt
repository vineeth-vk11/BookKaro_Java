package com.example.bookkaro.loginui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.bookkaro.LoginActivity
import com.example.bookkaro.MainActivity
import com.example.bookkaro.R
import com.example.bookkaro.databinding.FragmentLoginEnterDetailsBinding
import kotlinx.android.synthetic.main.fragment_login_enter_details.*

class LoginEnterDetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginEnterDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_enter_details, container, false)

        binding.getStartedButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root

    }
}