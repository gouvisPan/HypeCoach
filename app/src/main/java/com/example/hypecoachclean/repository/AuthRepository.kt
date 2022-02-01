package com.example.hypecoachclean.repository


import com.example.hypecoachclean.Resource
import com.example.hypecoachclean.data.POJOs.User
import com.example.hypecoachclean.network.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(): BaseRepository () {

    private val auth = FirebaseSource()

    suspend fun  login(
        email: String,
        password: String,
        ): Resource<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            val user = auth.login(email, password)
            if (user == null) {
                Resource.Failure("Login Failure")
            } else {
                Resource.Success(user)

            }
        }
    }

    suspend fun  register(
        name : String,
        email: String,
        password: String,
    ): Resource<FirebaseUser> {
        return withContext(Dispatchers.IO) {
            val user = auth.register(name,email, password)
            if (user == null) {
                Resource.Failure("Registration Failure")
            } else {
                Resource.Success(user)

            }
        }
    }

    suspend fun  autoLogin(): Boolean {
        return withContext(Dispatchers.IO) {
            auth.autoLogin()
        }
    }


}