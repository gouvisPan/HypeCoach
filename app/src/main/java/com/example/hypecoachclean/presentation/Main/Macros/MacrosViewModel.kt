package com.example.hypecoachclean.presentation.Main.Macros

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.BusinessLogic.MicroCycle
import com.example.hypecoachclean.data.BusinessLogic.User
import com.example.hypecoachclean.data.BusinessLogic.Weight
import com.example.hypecoachclean.repository.UserRepository
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MacrosViewModel(context: Context) : ViewModel() {

    private var  userRepository= UserRepository(context)
    //Vars
    private lateinit var myUser: User
    private var macrosArray : ArrayList<Int>? = null
    private var userAge: Int = 0
    private var userGender: String = ""
    private var userHeight: Double = 0.0
    private var microCycle: MicroCycle? = null
    private var weightLog = ArrayList<Weight>()
    private var weekAverageWeight = 0.0
    private var maintenanceCalories = 0
    private var goalCalories = 0
    private var inBulking = true
    //LiveData Vars
    val userL = MutableLiveData<User>()
    val macrosL = MutableLiveData<ArrayList<Int>>()
    val adherenceL = MutableLiveData<Boolean>()
    val isWeightLogSufficientL = MutableLiveData<Boolean>()
    val isWeightLogEmpty = MutableLiveData<Boolean>(false)
    val isDataSufficient =  MutableLiveData<Boolean>(true)
    val areStatsUnhealthyL = MutableLiveData<Boolean>()

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            myUser = userRepository.getUser()

            getUserInfo(myUser)
            userL.postValue(myUser)
            macrosL.postValue(myUser.macros)
        }
    }

    private fun checkLog(){
        if(myUser.log.isNullOrEmpty()){
            isWeightLogEmpty.postValue(true)
        }
    }

    private fun calculateMacros(){
        if(macrosArray!![0] == 0 && macrosArray!![1] == 0 && macrosArray!![2] == 0) {
            calculateStartingCalories()
            calculateMacrosFromCalories()
        }else{
            readjustCaloriesFromLastWeek()
        }
        macrosL.postValue(macrosArray!!)
    }

    private fun calculateMacrosFromCalories(){
        var remainingKcal = goalCalories
        macrosArray!![Constants.GOAL_POSITION]=goalCalories
        macrosArray!![Constants.MAINT_POSITION]=maintenanceCalories

        if(inBulking) {//splitting available calories to macros
            macrosArray!![1] = (weekAverageWeight * 2.4).toInt()
            remainingKcal -= macrosArray!![1] * 4
            macrosArray!![2] = (weekAverageWeight * 0.9).toInt()
            remainingKcal -= macrosArray!![1] * 9
            macrosArray!![0] = remainingKcal / 4

            calibrateMacros()

        }else{
            macrosArray!![1] = (weekAverageWeight * 2.2).toInt()
            remainingKcal -= macrosArray!![1] * 4

            macrosArray!![2] = (weekAverageWeight * 0.7).toInt()
            remainingKcal -= macrosArray!![1] * 9

            macrosArray!![0] = remainingKcal / 4

            calibrateMacros()
        }
    }

    private fun  readjustCaloriesFromLastWeek(): Int{
        val size = weightLog.size
        println(size)
        maintenanceCalories = macrosArray!![Constants.MAINT_POSITION]
        goalCalories=macrosArray!![Constants.GOAL_POSITION]
        weekAverageWeight = calculateAverageWeight(size - 7, size-1)

        if(size > 13) { //In case of insufficient data certain calculations will include null values
            val weightDiff = weekAverageWeight - calculateAverageWeight(size-14,size-8)

            val idealGainLowerLimit = weekAverageWeight*0.0025  //setting the Ideal weight difference
            val idealGainUpperLimit = weekAverageWeight*0.005

            val idealLossLowerLimit = -weekAverageWeight*0.005
            val idealLossUpperLimit = -weekAverageWeight*0.01

            var change=0

            if(inBulking){//translating weight difference to caloric adjustments
                when{
                    weightDiff < 0 -> {change = 500}
                    weightDiff < idealGainLowerLimit*0.8-> {change = 250}
                    weightDiff > idealGainUpperLimit*0.9 -> {change = -250}
                }
            }else {
                when{
                    weightDiff > 0 -> {change = -500}
                    weightDiff < idealLossLowerLimit*0.8-> {change = -250}
                    weightDiff > idealLossUpperLimit-> {change = 250}
                }
            }
            goalCalories+=change
            maintenanceCalories+=change
            calculateMacrosFromCalories()
        }else{
            isWeightLogSufficientL.postValue(false)
        }
        return 1
    }

    private fun calibrateMacros(){ //In extreme cases recalibration is needed in order to reassure that the user
        //is not getting unhealthy
        if(macrosArray!![0]<50){
            macrosArray!![0]+=24
            macrosArray!![1]-=10
            macrosArray!![2]-=6
        }
        if (macrosArray!![0]<20 || macrosArray!![1]<weekAverageWeight * 1.8 || macrosArray!![2]<weekAverageWeight * 0.4){
            calculateStartingCalories()
            areStatsUnhealthyL.postValue(true)
            calculateMacrosFromCalories()
        }
    }

    private fun calculateStartingCalories(){ //initial calorie calculator based on user info and activity
        var BMR = 0.0
        var AMR = 0.0

        weekAverageWeight=calculateAverageWeight(weightLog.size - 7, weightLog.size-1)

        println("$userGender $weekAverageWeight $userHeight $userAge")
        if(userGender == "Male"){           //Calculating the BMR - Basic Metabolic Rate
            BMR =66.47+(13.75*weekAverageWeight) + (5.003*userHeight*100) - (6.755*userAge)
        }else if(userGender == "Female"){
            BMR = 655.1+(9.563*weekAverageWeight) + (1.850*userHeight*100) - (4.676*userAge)
        }

        when(microCycle!!.sessions.size){        //Calculating the AMR - Active Metabolic Rate
            4->AMR = BMR*1.48
            5->AMR = BMR*1.55
            6->AMR = BMR*1.67
        }
        maintenanceCalories = AMR.toInt()

        if(inBulking){
            goalCalories = maintenanceCalories + 300
        }else{
            goalCalories = maintenanceCalories - 700
        }
    }
    private fun adherenceCheck(adh: Int){
        if(adh > 90){
            adherenceL.postValue(true)
        }else{
            adherenceL.postValue(false)
        }
    }

    private fun getUserInfo(user:User){
        val turnsType = object : TypeToken<ArrayList<Weight>>() {}.type

        if(user.height.isEmpty() || user.birthString.isEmpty()){
            isDataSufficient.postValue(false)

        }else{
            macrosArray = user.macros
            userGender = user.genderString
            userHeight = user.height.toDouble()
            userAge =
                Calendar.getInstance().get(Calendar.YEAR) - user.birthString.takeLast(4).toInt()

            goalCalories = macrosArray!![Constants.GOAL_POSITION]
            maintenanceCalories = 0

            microCycle= Gson().fromJson(user.program,MicroCycle::class.java)
            weightLog = Gson().fromJson(user.log, turnsType)
            println("The size: " + weightLog.size)
            inBulking = microCycle!!.gainMuscle
        }
        adherenceCheck(myUser.adherence)
    }

    private fun calculateAverageWeight(startingIndex: Int, endingIndex: Int): Double{
        var weightSum=0.0
        var weightAvg = 0.0

        for(i in startingIndex..endingIndex) {
            weightSum += weightLog[i].value
        }
        weightAvg = (weightSum/(Math.abs((endingIndex - (startingIndex - 1)))))
        return weightAvg
    }

    fun updateMacrosIfPossible(){
        if(weightLog.size > 8){
            calculateMacros()
            updateMacros()
            println("UpdateMacrosIfPossible")
        }else{
            isWeightLogSufficientL.postValue(false)
        }
    }

    private fun updateMacros(){
        viewModelScope.launch(Dispatchers.IO){
            val myHashMap = HashMap<String, Any>()
            myHashMap[Constants.ADHERENCE] = 0
            userRepository.updateRemote(myHashMap)

            val myHashMapA = HashMap<String, ArrayList<Int>>()
            myHashMapA[Constants.MACROS] = macrosArray!!

            userRepository.updateRemoteArray(myHashMapA)

            myUser.adherence=0
            userRepository.addUser(myUser)

            adherenceL.postValue(false)
        }
    }
}