package com.example.hypecoachclean.presentation.Main.CreateProgram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.R
import com.example.hypecoachclean.data.POJOs.MicroCycle
import com.example.hypecoachclean.databinding.FragmentCreateProgramBinding
import com.example.hypecoachclean.databinding.FragmentWeeklyProgramBinding
import com.example.hypecoachclean.presentation.Main.WeeklyProgram.WeeklyProgramViewModel
import com.example.hypecoachclean.presentation.base.BaseFragment


class CreateProgramFragment :  BaseFragment<CreateProgramViewModel, FragmentCreateProgramBinding>() {

    private var mMicroCycle= MicroCycle(0,"","",true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbMaintainMuscle.setOnClickListener {
            binding.rb6Days.isEnabled = false
            binding.rb5Days.isChecked = true
        }

        binding.rbGainMuscle.setOnClickListener {
            binding.rb6Days.isEnabled = true
        }

        binding.btnGenerateMacroCycle.setOnClickListener {
            binding.loadingViewProgressBar.visibility = View.VISIBLE
            getInfo()
            generateProgram()
            viewModel.saveProgram(mMicroCycle)
        }

        observeViewModel()
    }

    private fun observeViewModel(){

        viewModel.savedL.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.loadingViewProgressBar.visibility = View.GONE
            }
        })
    }

    private fun getInfo(){
        mMicroCycle.workoutsPerWeek = getWorkoutsPerWeek()
        mMicroCycle.primaryFocus = getPrimaryFocus()
        mMicroCycle.secondaryFocus = getSecondaryFocus()
        mMicroCycle.gainMuscle = binding.rbGainMuscle.isChecked
        mMicroCycle.startingVolume = getCurrentVolume()

    }
    private fun getPrimaryFocus(): String{

        var muscleGroup = ""

        if(binding.rbFocusChest.isChecked){
            muscleGroup = "Chest"
        }
        if(binding.rbFocusBack.isChecked){
            muscleGroup = "Back"
        }
        if(binding.rbFocusLegs.isChecked){
            muscleGroup = "Legs"
        }

        return muscleGroup

    }
    private fun getCurrentVolume(): ArrayList<Int>{ //Reading the current volume that the user is
        //used to
        var setArray : ArrayList<Int> = arrayListOf(0,0,0,0,0,0,0,0,0,0,0)


        setArray[Constants.CHEST_POSITION]= binding.etChestVolume.text.toString().toInt()
        setArray[Constants.BACK_POSITION]= binding.etBackVolume.text.toString().toInt()
        setArray[Constants.TRICEPS_POSITION]= binding.etTricepsVolume.text.toString().toInt()
        setArray[Constants.BICEPS_POSITION]= binding.etBicepsVolume.text.toString().toInt()
        setArray[Constants.QUADS_POSITION]= binding.etQuadsVolume.text.toString().toInt()
        setArray[Constants.HAMSTRINGS_POSITION]= binding.etHamstringsVolume.text.toString().toInt()

        return setArray
    }

    private fun generateProgram(){ //MicroCycle object will handle the program generation

        mMicroCycle.generateCycle()
        mMicroCycle.sessions[0].setNext(true)
        mMicroCycle.printMicro()

    }

    private fun getSecondaryFocus(): String{

        var muscleGroup = ""

        if(binding.rbFocusTriceps.isChecked){
            muscleGroup = "Triceps"
        }
        if(binding.rbFocusBiceps.isChecked){
            muscleGroup = "Biceps"
        }
        if(binding.rbFocusDelts.isChecked){
            muscleGroup = "Delts"
        }

        return muscleGroup
    }
    private fun getWorkoutsPerWeek(): Int{

        var days = 0

        if(binding.rb4Days.isChecked){
            days = 4
        }
        if(binding.rb5Days.isChecked){
            days = 5
        }
        if(binding.rb6Days.isChecked){
            days = 6
        }

        return days
    }


    override fun getViewModel()= CreateProgramViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentCreateProgramBinding.inflate(inflater,container,false)

}