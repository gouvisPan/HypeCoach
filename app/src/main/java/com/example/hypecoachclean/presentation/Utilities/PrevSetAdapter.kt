package com.example.hypecoachclean.presentation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoachclean.data.POJOs.Session

import com.example.hypecoachclean.data.POJOs.Set
import com.example.hypecoachclean.databinding.SetTableRowBinding


class PrevSetAdapter(
    var sets: ArrayList<Set>
) : RecyclerView.Adapter<PrevSetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = SetTableRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)

    }

    fun updateSets(theSets: ArrayList<Set>){
        sets = theSets
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sets[position])

    }

    override fun getItemCount(): Int {
        return sets.size
    }

    fun getItems(): ArrayList<Set> {
        return sets
    }

    class ViewHolder(binding: SetTableRowBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvSetNum = binding.tvSetNum
        val tvLoadNum = binding.tvLoad
        val tvRepNum = binding.tvRepNum
        val tvRirNum = binding.tvRirNum

        fun bind(theSet: Set){

           tvSetNum.text = theSet.id.toString()
           tvLoadNum.text = theSet.load.toString()
           tvRepNum.text = theSet.reps.toString()
            tvRirNum.text = theSet.rir.toString()
        }
    }
}

