package com.example.hypecoachclean.presentation.Main.CreateProgram

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.BusinessLogic.MicroCycle
import com.example.hypecoachclean.objectToString
import com.example.hypecoachclean.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateProgramViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)

    val savedL = MutableLiveData<Boolean>()

    fun saveProgram(micro: MicroCycle){
        viewModelScope.launch(Dispatchers.IO){
            val myUser = userRepository.getUser()
            var myHashMap = HashMap<String,Any>()
            myUser.adherence = 0
            myUser.program = objectToString(micro)

            userRepository.addUser(myUser)
            myHashMap[Constants.PROGRAM] =  myUser.program
            userRepository.updateRemote(myHashMap)
            savedL.postValue(true)
        }
    }
}