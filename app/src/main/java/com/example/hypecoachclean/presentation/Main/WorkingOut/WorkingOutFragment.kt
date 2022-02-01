package com.example.hypecoachclean.presentation.Main.WorkingOut

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hypecoachclean.R
import com.example.hypecoachclean.data.POJOs.Set
import com.example.hypecoachclean.databinding.FragmentWorkingOutBinding
import com.example.hypecoachclean.presentation.PrevSetAdapter
import com.example.hypecoachclean.presentation.Utilities.CurrentSetAdapter
import com.example.hypecoachclean.presentation.base.BaseFragment

class WorkingOutFragment : BaseFragment<WorkingOutViewModel, FragmentWorkingOutBinding>() {

    private val prevSetAdapter = PrevSetAdapter(arrayListOf())
    private var currSetAdapter = CurrentSetAdapter(arrayListOf())
    private var isTimerRunning = false
    private var doubleBackToExitPressed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapters()
        arguments.let{
            viewModel.getExercise(WorkingOutFragmentArgs.fromBundle(it!!).sessionId.toInt())
        }
        setOnClickListeners()
        observeViewModel()
    }
    private fun setOnClickListeners(){

        binding.btnNext.setOnClickListener {
            viewModel.nextExercise(currSetAdapter.sets)

        }

        binding.btnPrev.setOnClickListener {
            viewModel.prevExercise()
        }

        binding.llTimerE.setOnClickListener {

            if(isTimerRunning){
                viewModel.stopTimer()
                isTimerRunning = false
            }else{
                viewModel.startTimer()
                isTimerRunning = true
            }
        }

        binding.btnAdd30.setOnClickListener {
            viewModel.add30()
        }
    }
    private fun observeViewModel(){

        viewModel.hExerciseL.observe(viewLifecycleOwner, Observer {

            binding.loadingViewProgressBar.visibility = View.GONE
            binding.tvExerciseName.text = it.getExercise().getName()
            binding.tvExerciseRepRange.text= it.getExercise().getRange()
            binding.tvTimerE.text=it.getExercise().getRest().toString()

            prevSetAdapter.updateSets(it.getSets())

        })

        viewModel.cExerciseL.observe(viewLifecycleOwner, Observer {
            setCurrentRV(it.getSets())
        })

        viewModel.timeCount.observe (viewLifecycleOwner, Observer {
            binding.tvTimerE.text = it.toString()
        })

        viewModel.positionL.observe(viewLifecycleOwner, Observer {
            when(it){
                0 -> binding.btnPrev.visibility = View.GONE
                1 -> binding.btnPrev.visibility = View.VISIBLE
            }
        })

        viewModel.lastExerciseL.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.btnNext.text = "END SESSION"
            }else{
                binding.btnNext.text = "NEXT"
            }
        })

        viewModel.workoutSavedL.observe(viewLifecycleOwner, Observer {
            if(it){
                val action: NavDirections = WorkingOutFragmentDirections.actionGoToInput(viewModel.sessionNum.toLong())
                view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
            }
        })
    }

    private fun setCurrentRV(sets: ArrayList<Set>){
        currSetAdapter = CurrentSetAdapter(sets)

        binding.rvCurrentWeekSets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currSetAdapter
        }

    }

    private fun setAdapters(){
        binding.rvPastWeekSets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = prevSetAdapter
        }

        binding.rvCurrentWeekSets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currSetAdapter
        }
    }


    override fun getViewModel()= WorkingOutViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentWorkingOutBinding.inflate(inflater,container,false)

}