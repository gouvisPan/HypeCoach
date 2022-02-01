package com.example.hypecoachclean.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.hypecoachclean.R
import com.example.hypecoachclean.Resource
import com.example.hypecoachclean.databinding.FragmentRegisterBinding
import com.example.hypecoachclean.databinding.FragmentSplashBinding
import com.example.hypecoachclean.presentation.Main.MainActivity
import com.example.hypecoachclean.presentation.base.BaseFragment
import com.example.hypecoachclean.startNewActivity
import com.example.hypecoachclean.visible


class RegisterFragment :  BaseFragment<AuthViewModel, FragmentRegisterBinding>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            register()
        }

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {

            when (it){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Registered", Toast.LENGTH_SHORT).show()
                    requireActivity().startNewActivity(MainActivity::class.java)

                }
                is Resource.Failure ->{
                    binding.progressbar.visible(false)
                    Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun register(){

        val name = binding.etNameSignUp.text.toString().trim()
        val mail = binding.etUserEmailSignUp.text.toString().trim()
        val pass = binding.etUserPasswordSignUp.text.toString()

        binding.progressbar.visible(true)

        viewModel.register(name,mail,pass)
    }


    override fun getViewModel()= AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentRegisterBinding.inflate(inflater,container,false)
}
