package com.example.hypecoachclean.presentation.Utilities

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hypecoachclean.data.BusinessLogic.Set
import com.example.hypecoachclean.databinding.CurrentSetTableRowBinding
import com.example.hypecoachclean.round
import kotlin.math.floor

class CurrentSetAdapter(
    var sets: ArrayList<Set>,
    var isTheFirstRun: Boolean = true
) : RecyclerView.Adapter<CurrentSetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val binding = CurrentSetTableRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)

    }


    fun secondRun(){
        isTheFirstRun = false
    }

    fun updateSets(theSets: ArrayList<Set>){
        sets = theSets
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sets[position],isTheFirstRun)

    }

    override fun getItemCount(): Int {
        return sets.size
    }

    fun getItems(): ArrayList<Set> {
        return sets
    }

    class ViewHolder(binding: CurrentSetTableRowBinding) : RecyclerView.ViewHolder(binding.root) {

        private val tvSetNum = binding.tvSetNumCurr
        private val etLoadNum = binding.etLoad
        private val etRepNum = binding.etRepNum
        private val etRirNum = binding.etRirNum


        fun bind(theSet: Set,isTheFirstRun: Boolean){

            etLoadNum.text!!.clear()
            etRepNum.text!!.clear()
            etRirNum.text!!.clear()


            tvSetNum.text = theSet.id.toString()

            if(theSet.load == floor(theSet.load)) {
                etLoadNum.hint = theSet.load.toInt().toString()
            }else{
                etLoadNum.hint = theSet.load.round(2).toString()
            }

            etRepNum.hint = theSet.reps.toString()
            etRirNum.hint = theSet.rir.toString()



            etLoadNum.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (s.isNotEmpty()) {
                        theSet.load = s.toString().toDouble()
                    }
                }
            })
           etRepNum.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (s.isNotEmpty()) {
                        theSet.reps = s.toString().toInt()
                    }
                }
            })
            etRirNum.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (s.isNotEmpty()) {
                        theSet.rir = s.toString().toInt()
                    }
                }
            })
        }
        }
}


