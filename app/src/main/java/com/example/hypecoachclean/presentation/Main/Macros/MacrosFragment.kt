package com.example.hypecoachclean.presentation.Main.Macros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.FragmentMacrosBinding
import com.example.hypecoachclean.enable
import com.example.hypecoachclean.presentation.Main.WorkingOut.WorkingOutFragmentArgs
import com.example.hypecoachclean.presentation.base.BaseFragment

class MacrosFragment : BaseFragment<MacrosViewModel,FragmentMacrosBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()

        binding.btnUpdateMacros.setOnClickListener {
            viewModel.updateMacrosIfPossible()
        }

        observeViewModel()
    }
    private fun observeViewModel(){

        viewModel.macrosL.observe(viewLifecycleOwner,Observer{

            if(it.isNullOrEmpty()){
                binding.tvProteinValue.text = " "
                binding.tvCarbValue.text = " "
                binding.tvFatsValue.text = " "
                binding.tvGoalCalories.text = " "
                binding.tvMaintenanceCalories.text = " "
            }else {
                binding.tvProteinValue.text = it[Constants.PROTEIN_POSITION].toString()
                binding.tvCarbValue.text = it[Constants.CARBS_POSITION].toString()
                binding.tvFatsValue.text = it[Constants.FATS_POSITION].toString()
                binding.tvGoalCalories.text = it[Constants.GOAL_POSITION].toString()
                binding.tvMaintenanceCalories.text = it[Constants.MAINT_POSITION].toString()
            }
        })

        viewModel.isDataSufficient.observe(viewLifecycleOwner,Observer{
            if(!it){
                binding.btnUpdateMacros.enable(false)
                Toast.makeText(
                    requireContext(),
                    "Please fill your profile information!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        viewModel.adherenceL.observe(viewLifecycleOwner,Observer{
            if(!it){
                binding.btnUpdateMacros.setBackgroundColor(resources.getColor(R.color.colorGreyDark))
                binding.btnUpdateMacros.isEnabled = false
                binding.tvAdherenceHint.visibility = View.VISIBLE
            }
        })

        viewModel.isWeightLogSufficientL.observe(viewLifecycleOwner,Observer{
            if(!it){
                Toast.makeText(
                    requireContext(),
                    "Please fill the weight information needed in the weight log!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        viewModel.areStatsUnhealthyL.observe(viewLifecycleOwner,Observer{
            if(it) {
                Toast.makeText(
                    requireContext(), "Your daily food income is becoming unhealthy..." +
                            "Recalibrating your intake!", Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun getViewModel()= MacrosViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentMacrosBinding.inflate(inflater,container,false)

}