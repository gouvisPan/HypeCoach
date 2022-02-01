package com.example.hypecoachclean.presentation.Main.ChallengeAndPumpInput

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.POJOs.User
import com.example.hypecoachclean.objectToString
import com.example.hypecoachclean.repository.UserRepository
import com.example.hypecoachclean.stringToObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeAndPumpViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)
    val usedMusclesL = MutableLiveData<ArrayList<String>>()
    val challengePumpSavedL = MutableLiveData(false)


    var currentSessionId = 0
    lateinit var user : User

    private var volumeAdjustmentArray = arrayListOf<Int>(0,0,0,0,0,0,0,0,0,0,0)

    fun getData(sessionId: Int){
        currentSessionId = sessionId
        viewModelScope.launch(Dispatchers.IO){
            user = userRepository.getUser()

            val muscleInts = stringToObject(user.program).sessions[sessionId].volumeList
            val muscleStrings = ArrayList<String>()

            for((index,value) in muscleInts.withIndex()){
                if(value != 0) muscleStrings.add(Constants.intToMuscle(index))

            }
            var sets = stringToObject(user.program).sessions[sessionId].exercises[1].getSets()
            println("The load:" + sets[0].load)
            usedMusclesL.postValue(muscleStrings)

        }
    }
    fun convertToVolume(volumeArray: ArrayList<Int>){
        val muscleInts = stringToObject(user.program).sessions[currentSessionId].volumeList
        var i =0
        for((index,value) in muscleInts.withIndex()){
            if(value != 0){
                volumeAdjustmentArray[index] = volumeArray[i]
                i++
            }
        }
        saveChallengePumpInput()
    }

    private fun saveChallengePumpInput(){
        viewModelScope.launch(Dispatchers.IO){
            val micro = stringToObject(user.program)
            val myHashMap = HashMap<String,Any>()
            micro.volumeAdjustment = volumeAdjustmentArray

            user.program = objectToString(micro)

            myHashMap[Constants.PROGRAM] =  user.program
            userRepository.updateRemote(myHashMap)

            userRepository.addUser(user)
            challengePumpSavedL.postValue(true)
        }
    }


}