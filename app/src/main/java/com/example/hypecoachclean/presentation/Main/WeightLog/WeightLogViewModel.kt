package com.example.hypecoachclean.presentation.Main.WeightLog

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.BusinessLogic.Weight
import com.example.hypecoachclean.repository.UserRepository
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashMap
import kotlin.collections.ArrayList

class WeightLogViewModel(context: Context): ViewModel() {
        private var  userRepository= UserRepository(context)

        var allCompletedDatesListL = MutableLiveData <ArrayList<Weight>>()
        var noLogsL = MutableLiveData(false)

        var myWeights = ArrayList<Weight>()
        var weightChangesL = MutableLiveData <ArrayList<Weight>>()

        fun getWeightLog(){
            viewModelScope.launch(Dispatchers.IO){
                val mUser = userRepository.getUser()
                val turnsType = object : TypeToken<ArrayList<Weight>>() {}.type
                val myWeightArray = Gson().fromJson<ArrayList<Weight>>(mUser.log,turnsType)
                if(myWeightArray.isNullOrEmpty()) {
                    noLogsL.postValue(true)
                }else {
                    myWeights = myWeightArray
                    allCompletedDatesListL.postValue(myWeightArray)
                }
            }
        }

        fun deleteWeight(position: Int){
            myWeights.removeAt(position)
            saveWeightLogChanges()
        }

        fun addWeight(date: String, value: String){
            val weight = isDateLogged(date)  //checking if the date is already in the array

            if (weight == null) {
                var index=myWeights.size+1

                //Custom input shorting based on date
                for((j,i) in myWeights.withIndex()) {
                    if(getYear(i.date) <= getYear(date))
                        if(getMonth(i.date) <= getMonth(date))
                            if(getDay(i.date) < getDay(date))
                                index=j+1
                }
                if( index==myWeights.size+1) {
                    myWeights.add(Weight(0, date, value.toDouble()))
                }else{
                    myWeights.add(index, Weight(0, date, value.toDouble()))
                }

            } else {
                weight.value=value.toDouble()
            }
            allCompletedDatesListL.value = myWeights
            saveWeightLogChanges()

        }

    private  fun saveWeightLogChanges(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()
            val myWeightsString = Gson().toJson(myWeights)
            val myHashMap = HashMap<String, Any>()
            user.log = myWeightsString
            userRepository.addUser(user)
            weightChangesL.postValue(myWeights)

            myHashMap[Constants.WEIGHTLOG] = myWeightsString
            userRepository.updateRemote(myHashMap)
        }
    }

    private fun isDateLogged(date: String): Weight?{
        var weight : Weight? = null

        for(i in myWeights){
            if(i.date == date) {
                weight=i
            }
        }
        return weight
    }

    private fun getYear(date: String):Int{
        return  date.takeLast(4).toInt()
    }

    private fun getDay(date: String):Int{
        return if(date[1]=='/') {
            println(date[0].toString())
            date[0].toString().toInt()

        }else{
            println(date[0].toString() + date[1].toString())
            date.take(2).toInt()
        }
    }

    private fun getMonth(date: String):Int{
        var tmpStr=date.takeLast(7)
        var monthStr=""
        if(tmpStr[0] == '/'){
            monthStr = tmpStr[1].toString()
        }else{
            monthStr = tmpStr[0].toString() + tmpStr[1].toString()
        }

        return monthStr.toInt()
    }
}