package com.example.hypecoachclean.presentation.Main

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.FragmentMainScreenBinding
import com.example.hypecoachclean.databinding.NavigationHeaderBinding
import com.example.hypecoachclean.enable
import com.example.hypecoachclean.presentation.auth.AuthActivity
import com.example.hypecoachclean.presentation.base.BaseFragment
import com.example.hypecoachclean.startNewActivity


class MainScreenFragment() :  BaseFragment<MainViewModel, FragmentMainScreenBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val viewHeader = binding.navigationView.getHeaderView(0)
        val navViewHeaderBinding : NavigationHeaderBinding = NavigationHeaderBinding.bind(viewHeader)
        val typeFace: Typeface = Typeface.createFromAsset(activity!!.assets,"fonts/BlackOpsOne-Regular.ttf")
        binding.btnWorkouts.enable(false)
        binding.tvMainLogo.typeface = typeFace
        viewModel.loadUserData()

        setNavMenuActions()
        btnWeeklyProgramOnPress()
        btnCreateProgramOnPress()
        observeViewModel(navViewHeaderBinding)
    }
    private fun setNavMenuActions(){

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_menu_my_profile -> {
                    findNavController().navigate(R.id.action_mainScreenFragment_to_profileFragment)
                }
                R.id.nav_menu_weight_log -> {
                    findNavController().navigate(R.id.action_mainScreenFragment_to_weightLogFragment)
                }
                R.id.nav_menu_disruption -> {
                    viewModel.isMicroGenerated.observe(viewLifecycleOwner,Observer{i->
                        if(i){
                            findNavController().navigate(R.id.action_mainScreenFragment_to_disruptionInputFragment)
                        }else{
                            Toast.makeText(requireContext(), "You need to create a program first!", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                R.id.nav_menu_my_Macros -> {
                    viewModel.hasWeightLogged.observe(viewLifecycleOwner,Observer{i->
                        if(i){
                            findNavController().navigate(R.id.action_mainScreenFragment_to_macrosFragment)
                        }else{
                            Toast.makeText(requireContext(), "You need to log weight data first", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                R.id.nav_menu_sign_out-> {
                    viewModel.logout()
                }


            }
            return@setNavigationItemSelectedListener true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel(navViewHeaderBinding: NavigationHeaderBinding){
        viewModel.dataLoaded.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.getUser()
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer {user->

            binding.btnWorkouts.enable(true)
            binding.loadingViewProgressBar.visibility= View.GONE
            binding.tvAdherencePoints.text ="Adherence points: " + user.adherence.toString() +"/100"
            navViewHeaderBinding.drawerUserName.text = user.name

            Glide.with(this)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(navViewHeaderBinding.drawerUserImg)

        })

        viewModel.logoutL.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(activity, "Logged Out", Toast.LENGTH_SHORT).show()
                requireActivity().startNewActivity(AuthActivity::class.java)
            }

        })
    }

    private fun btnWeeklyProgramOnPress(){
        binding.btnWorkouts.setOnClickListener {
            viewModel.isMicroGenerated.observe(viewLifecycleOwner,Observer{
                if(it){
                    findNavController().navigate(R.id.action_mainScreenFragment_to_weeklyProgramFragment)
                }else{
                    Toast.makeText(requireContext(), "You need to create a program first!", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }

    private fun btnCreateProgramOnPress(){
        binding.btnCreateWorkout.setOnClickListener{
            findNavController().navigate(R.id.action_mainScreenFragment_to_createProgramFragment)
        }
    }


    override fun getViewModel()= MainViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentMainScreenBinding.inflate(inflater,container,false)

}