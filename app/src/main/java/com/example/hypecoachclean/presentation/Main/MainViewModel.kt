package com.example.hypecoachclean.presentation.Main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.data.BusinessLogic.User
import com.example.hypecoachclean.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context: Context): ViewModel() {

    private var  userRepository = UserRepository(context)
    private lateinit var  mUser: User
    //LiveData Vars
    val user = MutableLiveData<User>()
    val dataLoaded = MutableLiveData(false)
    val logoutL = MutableLiveData(false)

        //Unregistered Info Vars
    val hasWeightLogged = MutableLiveData(true)
    val isMicroGenerated = MutableLiveData(true)


    fun loadUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loadUserData()
            dataLoaded.postValue(true)
        }
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            mUser = userRepository.getUser()
            checkUnregisteredInfo()
            user.postValue(mUser)
        }
    }
    private fun checkUnregisteredInfo(){
        if(mUser.log.isEmpty()){
            hasWeightLogged.postValue(false)
        }else{
            hasWeightLogged.postValue(true)
        }

        if(mUser.program.isNullOrEmpty()){
            isMicroGenerated.postValue(false)
        }else{
            isMicroGenerated.postValue(true)
        }
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            userRepository.addUser(user)
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logout()
            logoutL.postValue(true)
        }
    }

}
