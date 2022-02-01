package com.example.hypecoachclean.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hypecoachclean.*
import com.example.hypecoachclean.databinding.FragmentLoginBinding
import com.example.hypecoachclean.presentation.Main.MainActivity
import com.example.hypecoachclean.presentation.base.BaseFragment
import com.example.hypecoachclean.repository.AuthRepository


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)
        binding.btnSignIn.enable(false)

        setOnClickListeners()

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {

            when (it){
              is Resource.Success -> {
                  Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
                  requireActivity().startNewActivity(MainActivity::class.java)

              }
              is Resource.Failure ->{
                  binding.progressbar.visible(false)

                  Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
              }
            }
        })


    }
    private fun setOnClickListeners(){

        binding.tvSwitchToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.etUserPasswordSignIn.addTextChangedListener {
            val email = binding.etUserEmailSignIn.text.toString().trim()
            binding.btnSignIn.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.btnSignIn.setOnClickListener {
            binding.progressbar.visible(true)
            login()
        }

    }


    private fun login(){
        val email = binding.etUserEmailSignIn.text.toString().trim()
        val pass = binding.etUserPasswordSignIn.text.toString().trim()
        viewModel.login(email,pass)
    }

    override fun getViewModel()= AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater,container,false)




}