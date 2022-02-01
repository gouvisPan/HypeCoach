package com.example.hypecoachclean.presentation.Main

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.MainApplication.Companion.applicationContext
import com.example.hypecoachclean.data.POJOs.User
import com.example.hypecoachclean.data.db.UserDatabase
import com.example.hypecoachclean.repository.AuthRepository
import com.example.hypecoachclean.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)

    val user = MutableLiveData<User>()
    val dataLoaded = MutableLiveData(false)
    val logoutL = MutableLiveData(false)

    fun loadUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loadUserData()
            dataLoaded.postValue(true)
        }
    }

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            val mUser = userRepository.getUser()
            user.postValue(mUser)
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
