package com.example.hypecoachclean.presentation.Main.WeeklyProgram

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.data.POJOs.ExerciseInSession
import com.example.hypecoachclean.data.POJOs.MicroCycle
import com.example.hypecoachclean.repository.UserRepository
import com.example.hypecoachclean.stringToObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeeklyProgramViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)

    val microL = MutableLiveData<MicroCycle>()


    fun getMicro(){
        viewModelScope.launch(Dispatchers.IO){
            val microCycle = getProgram()
            microL.postValue(microCycle)
        }
    }

    private suspend fun getProgram():MicroCycle{
        val program = userRepository.getUser().program
        val micro = stringToObject(program)
        return micro
    }
}