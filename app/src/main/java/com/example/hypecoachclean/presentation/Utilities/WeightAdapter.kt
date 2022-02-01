package com.example.hypecoachclean.presentation.Utilities

import android.app.Activity
import android.content.ClipData
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoachclean.data.POJOs.Weight
import com.example.hypecoachclean.databinding.ItemWeightRowBinding

class WeightAdapter(
    var log: ArrayList<Weight>
) : RecyclerView.Adapter<WeightAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeightAdapter.ViewHolder {

        val binding = ItemWeightRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }
    fun notifyEditItem(position: Int){
        notifyItemChanged(position)
    }

    fun notifyDeleteItem(position: Int){
        log.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearETs() {

    }

    fun updateWeight(theLog: ArrayList<Weight>) {
        log = theLog
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WeightAdapter.ViewHolder, position: Int) {
        holder.bind(log[position],position)

    }

    fun getItem(position: Int):Weight {
        return log[position]
    }

    override fun getItemCount(): Int {
        return log.size
    }

    fun getTheLog(): ArrayList<Weight> {
        return log
    }

    class ViewHolder(binding: ItemWeightRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvLogNum = binding.tvPosition
        val tvValue  = binding.tvWeight
        val tvDate   = binding.tvDate
        val llname = binding.llWeightItemName

        fun bind(weight: Weight,pos: Int){

            tvLogNum.text= pos.toString()
            tvValue.text = weight.value.toString()
            tvDate.text  = weight.date

            if (pos % 2 == 0) {
                llname.setBackgroundColor(
                    Color.parseColor("#EBEBEB")
                )
            } else {
               llname.setBackgroundColor(
                    Color.parseColor("#FFFFFF")
                )
            }

        }
    }

}