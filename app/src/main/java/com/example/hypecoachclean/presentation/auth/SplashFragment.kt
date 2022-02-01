package com.example.hypecoachclean.presentation.auth

import android.graphics.*
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.FragmentSplashBinding
import com.example.hypecoachclean.presentation.Main.MainActivity
import com.example.hypecoachclean.presentation.base.BaseFragment
import com.example.hypecoachclean.startNewActivity


class SplashFragment :  BaseFragment<AuthViewModel, FragmentSplashBinding>()  {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeFace: Typeface = Typeface.createFromAsset(activity!!.assets,"fonts/BlackOpsOne-Regular.ttf")
        binding.tvAppName.typeface = typeFace
        Handler().postDelayed({
            viewModel.autoLogin()

        },1000)

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.autoLoginResponse.observe(viewLifecycleOwner, Observer {

            if(it){
                requireActivity().startNewActivity(MainActivity::class.java)
                Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_SHORT).show()
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }

        })



        }

    override fun getViewModel()= AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSplashBinding.inflate(inflater,container,false)





}