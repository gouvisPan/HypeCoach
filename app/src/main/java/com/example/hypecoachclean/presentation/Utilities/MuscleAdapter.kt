package com.example.hypecoachclean.presentation.Utilities

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoachclean.databinding.ChallengePumpInputRowBinding

class MuscleAdapter(
    var muscleGroups: ArrayList<String>
):RecyclerView.Adapter<MuscleAdapter.ViewHolder>() {

    var volumeArray = initializeArray(muscleGroups)

    private fun initializeArray(stringArray: ArrayList<String>) : ArrayList<Int>{
        val tmpIntArray : ArrayList<Int> = arrayListOf()

        for(i in stringArray){
            tmpIntArray.add(0)
        }
        println(tmpIntArray)
        return tmpIntArray
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChallengePumpInputRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)



        return ViewHolder(binding)

    }

    fun updateMuscles(theMuscles: ArrayList<String>){
        muscleGroups = theMuscles
        volumeArray = initializeArray(muscleGroups)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(muscleGroups[position],position)

    }

    override fun getItemCount(): Int {
        return muscleGroups.size
    }

    fun getVolume(): ArrayList<Int>{
        return volumeArray
    }


    inner class ViewHolder(binding: ChallengePumpInputRowBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvName = binding.tvChallengeRowMuscleGroup
        val sbChallenge = binding.sbInputChallengeValue
        val sbPump = binding.sbInputPumpValue



        fun bind(theMuscle: String,position: Int){
            tvName.text = theMuscle

            sbChallenge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    volumeArray[position] += i
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }


            })

            sbPump.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    volumeArray[position] += i
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }


            })
        }
    }
}