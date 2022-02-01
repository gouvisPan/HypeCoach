package com.example.hypecoachclean.presentation.Main.DIsruptionInput

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.FragmentDisruptionInputBinding
import com.example.hypecoachclean.presentation.DisruptionAdapter
import com.example.hypecoachclean.presentation.base.BaseFragment

class DisruptionInputFragment : BaseFragment<DisruptionInputViewModel,FragmentDisruptionInputBinding>() {

    private var disruptionAdapter = DisruptionAdapter(arrayListOf(0,0,0,0,0,0,0,0,0,0))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnSubmitDisruptionInfo.setOnClickListener {
            viewModel.updateVolume(disruptionAdapter.getMuscleGroupsDisruption())
        }
        setAdapter()
        viewModel.getMuscleGroups()
        observeViewModel()
    }

    private fun setAdapter(){
        binding.rvMuscleGroupsDisruption.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = disruptionAdapter
        }
    }
    private fun observeViewModel(){
        viewModel.muscleGroupsL.observe(viewLifecycleOwner, Observer{
            binding.loadingViewProgressBar.visibility = View.GONE
            disruptionAdapter.updateMuscles(it)

        })

        viewModel.savedL.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(R.id.action_disruptionInputFragment_to_mainScreenFragment)
            }

        })
    }

    override fun getViewModel()= DisruptionInputViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentDisruptionInputBinding.inflate(inflater,container,false)
}