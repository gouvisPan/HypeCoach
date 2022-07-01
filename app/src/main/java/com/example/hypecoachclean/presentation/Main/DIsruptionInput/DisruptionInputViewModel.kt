package com.example.hypecoachclean.presentation.Main.DIsruptionInput

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.BusinessLogic.MicroCycle
import com.example.hypecoachclean.data.BusinessLogic.User
import com.example.hypecoachclean.objectToString
import com.example.hypecoachclean.repository.UserRepository
import com.example.hypecoachclean.stringToObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DisruptionInputViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)
    private lateinit var user: User
    private lateinit var microCycle: MicroCycle

    var muscleGroupsL = MutableLiveData<ArrayList<Int>>()
    var savedL = MutableLiveData<Boolean>()

    fun getMuscleGroups(){
        viewModelScope.launch(Dispatchers.IO){
            user = userRepository.getUser()
            microCycle = stringToObject(user.program)
            muscleGroupsL.postValue(microCycle.volumeAdjustment)
        }
    }

    fun updateVolume(volumeArray: ArrayList<Int>){
        microCycle.volumeAdjustment = volumeArray
        microCycle.reAdjustVolumeForNextWeek()
        saveUpdate()
    }

    private fun saveUpdate(){
        viewModelScope.launch(Dispatchers.IO){
            val myHashMap = HashMap<String,Any>()
            myHashMap[Constants.PROGRAM] = objectToString(microCycle)
            userRepository.updateRemote(myHashMap)

            user.program = objectToString(microCycle)
            userRepository.addUser(user)

            savedL.postValue(true)
        }
    }
}