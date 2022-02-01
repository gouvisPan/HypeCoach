package com.example.hypecoachclean.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.R
import com.example.hypecoachclean.databinding.DisruptionInputRowBinding


class DisruptionAdapter (
    private var muscleGroups: ArrayList<Int>
    ): RecyclerView.Adapter<DisruptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =DisruptionInputRowBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
            return ViewHolder(binding)

    }
    fun updateMuscles(theMuscles: ArrayList<Int>){
        muscleGroups = theMuscles
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.bind(muscleGroups[position],position)


    }

    override fun getItemCount(): Int {
        return muscleGroups.size
    }

    fun getMuscleGroupsDisruption(): ArrayList<Int> {
        return muscleGroups

    }
    inner class ViewHolder(val binding: DisruptionInputRowBinding) : RecyclerView.ViewHolder(binding.root){

        private val tvMuscleName = binding.tvDisruptionRowMuscleGroup
        private val sbDisruption =binding.sbDisruptionRowDisruptionValue

        fun bind(theMuscle: Int,position: Int){
            tvMuscleName.text = Constants.intToMuscle(position)
            sbDisruption.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    muscleGroups[position] = i
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                }
            })
        }
    }



}