package com.example.hypecoachclean.presentation.Main.WeeklyProgram

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hypecoachclean.databinding.FragmentWeeklyProgramBinding
import com.example.hypecoachclean.presentation.base.BaseFragment
import com.example.hypecoachclean.presentation.*
import com.example.hypecoachclean.presentation.Utilities.ListAction

class WeeklyProgramFragment :  BaseFragment<WeeklyProgramViewModel, FragmentWeeklyProgramBinding>(),ListAction {

    private val microCycleAdapter = MicroCycleAdapter(arrayListOf(),this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvWeeklySessions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = microCycleAdapter
        }
        viewModel.getMicro()
        observeViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeViewModel(){
        viewModel.microL.observe(viewLifecycleOwner, Observer {
            binding.loadingViewProgressBar.visibility = View.GONE
            binding.rvWeeklySessions.visibility=View.VISIBLE
            microCycleAdapter.updateSessions(it.sessions)
            binding.tvMicroFocus.text = it.primaryFocus + " - " + it.secondaryFocus
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMicro()
    }
    private fun goToSession(view: View ,id: Long = 0L){
        val action: NavDirections = WeeklyProgramFragmentDirections.actionGoToSession(id)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onClick(id: Long) {
    view?.let { goToSession(it,id) }
    }

    override fun getViewModel()= WeeklyProgramViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentWeeklyProgramBinding.inflate(inflater,container,false)



}