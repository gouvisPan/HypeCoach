package com.example.hypecoachclean.repository

import androidx.lifecycle.LiveData
import com.example.hypecoachclean.data.POJOs.User

interface UserDataSource {

    suspend fun update(user: User)

    suspend fun get(): LiveData<User>


}