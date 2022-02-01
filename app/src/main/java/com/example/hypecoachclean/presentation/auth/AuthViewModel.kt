package com.example.hypecoachclean.presentation.auth

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.repository.AuthRepository
import com.example.hypecoachclean.Resource
import com.example.hypecoachclean.data.RoomUserDataSource
import com.example.hypecoachclean.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {

    private val authRepository =  AuthRepository()

    private val _loginResponse = MutableLiveData<Resource<FirebaseUser>>()
    val loginResponse: LiveData<Resource<FirebaseUser>>
        get() = _loginResponse

    private val _registerResponse = MutableLiveData<Resource<FirebaseUser>>()
    val registerResponse: LiveData<Resource<FirebaseUser>>
        get() = _registerResponse

    private val _autoLoginResponse= MutableLiveData<Boolean>()
    val autoLoginResponse: LiveData<Boolean>
        get() = _autoLoginResponse

   fun login(
        email: String,
        password: String
    )= viewModelScope.launch {
        _loginResponse.value = authRepository.login(email,password)
    }

    fun register(
        name: String,
        email: String,
        password: String
    )= viewModelScope.launch {
        _registerResponse.value = authRepository.register(name,email,password)

    }

    fun autoLogin(){
        viewModelScope.launch {
            _autoLoginResponse.value = authRepository.autoLogin()
        }
    }



    private fun areCredsValid(name: String , email: String, password: String): Boolean{

        return when{
            TextUtils.isEmpty(name) ->{
                //showErrorSnackBar("Name is missing")
                return false
            }
            TextUtils.isEmpty(email) ->{
                //showErrorSnackBar("Email is missing")
                return false
            }
            TextUtils.isEmpty(password) ->{
               // showErrorSnackBar("Password is missing")
                return false
            }else -> { return true }

        }
    }
}