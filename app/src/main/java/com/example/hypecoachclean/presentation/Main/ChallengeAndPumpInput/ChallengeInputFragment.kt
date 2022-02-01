package com.example.hypecoachclean.presentation.Main.ChallengeAndPumpInput

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.FragmentChalengeInputBinding
import com.example.hypecoachclean.presentation.Main.WorkingOut.WorkingOutViewModel

import com.example.hypecoachclean.presentation.Utilities.MuscleAdapter
import com.example.hypecoachclean.presentation.base.BaseFragment

class ChallengeInputFragment : BaseFragment<ChallengeAndPumpViewModel, FragmentChalengeInputBinding>() {

    private val muscleAdapter = MuscleAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val typeFace: Typeface = Typeface.createFromAsset(activity!!.assets,"fonts/BlackOpsOne-Regular.ttf")
        binding.tvChallengeAndPump.typeface = typeFace


        arguments.let{
            viewModel.getData(ChallengeInputFragmentArgs.fromBundle(it!!).sessionId.toInt())
        }
        setAdapter()
        setOnClickListeners()
        observeViewModel()
    }


    private fun setAdapter(){
        binding.rvMuscleGroupsChallenge.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = muscleAdapter
        }
    }

    private fun observeViewModel(){
        viewModel.usedMusclesL.observe(viewLifecycleOwner, Observer {
            muscleAdapter.updateMuscles(it)
        })

        viewModel.challengePumpSavedL.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(R.id.action_challengeInputFragment_to_mainScreenFragment)
            }
        })
    }
    private fun setOnClickListeners(){
        binding.btnSubmitDisruptionInfo.setOnClickListener {
            viewModel.convertToVolume(muscleAdapter.volumeArray)

        }
    }

    override fun getViewModel() = ChallengeAndPumpViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentChalengeInputBinding.inflate(inflater,container,false)


}