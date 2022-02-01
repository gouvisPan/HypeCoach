package com.example.hypecoachclean.presentation.Main.WeightLog

import android.app.DatePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoach_v11.utilities.SwipeToEditCallback
import com.example.hypecoachclean.databinding.FragmentWeightLogBinding
import com.example.hypecoachclean.presentation.SwipeToDeleteCallback
import com.example.hypecoachclean.presentation.Utilities.WeightAdapter
import com.example.hypecoachclean.presentation.base.BaseFragment
import java.text.SimpleDateFormat
import java.util.*

class WeightLogFragment : BaseFragment<WeightLogViewModel, FragmentWeightLogBinding>(){

    private var historyAdapter= WeightAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSelectDate.text =  getFormattedDate()
        viewModel.getWeightLog()
        setUpRV()
        setClickListeners()
        observeViewModel()
    }

    private fun setClickListeners(){
        binding.tvSelectDate.setOnClickListener {
            selectDate()
        }

        binding.btnAdd.setOnClickListener {
           val value = binding.etWeightValue.text.toString()
           val date = binding.tvSelectDate.text.toString()

            if (value.isNotEmpty() && date.isNotEmpty()) {
                viewModel.addWeight(date,value)
            }else{
                Toast.makeText(requireContext(),
                    "Please fill both the value and the date",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun observeViewModel(){
        viewModel.allCompletedDatesListL.observe(viewLifecycleOwner, Observer {
            //binding.loadingViewProgressBar.visibility = View.GONE
            historyAdapter.updateWeight(it)
        })

        viewModel.weightChangesL.observe(viewLifecycleOwner, Observer{
            if(it !=null) historyAdapter.updateWeight(it)
        })

    }

    fun setUpRV(){

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        val editSwipeHandler = object: SwipeToEditCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val log = historyAdapter.getItem(viewHolder.position)
                binding.tvSelectDate.text =log.date
                binding.etWeightValue.setText(log.value.toString())
                historyAdapter!!.notifyEditItem(viewHolder.position)
            }
        }

        ItemTouchHelper(editSwipeHandler).attachToRecyclerView(binding.rvHistory)

        val deleteSwipeHandler = object: SwipeToDeleteCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteWeight(viewHolder.position)
            }
        }
        ItemTouchHelper(deleteSwipeHandler).attachToRecyclerView(binding.rvHistory)
    }

    private fun getFormattedDate(): String {
        val sdf = SimpleDateFormat("d/MM/yyyy", Locale.ENGLISH)
        return sdf.format(Date()).toString()

    }

    private fun   selectDate(){ //DatePicker implementation

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(),
            { _, sYear, sMonth, sDay ->
                val selectedDate = "$sDay/${sMonth + 1}/$sYear"
                binding.tvSelectDate.text = selectedDate

            }, year, month, day)

        dpd.datePicker.maxDate = Date().time
        dpd.show()
    }





    override fun getViewModel()= WeightLogViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentWeightLogBinding.inflate(inflater,container,false)


}