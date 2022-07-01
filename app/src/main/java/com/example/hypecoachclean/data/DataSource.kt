package com.example.hypecoachclean.data

import com.example.hypecoachclean.data.BusinessLogic.User

interface DataSource {

    suspend fun addUser(user: User)

    suspend fun delete(user: User)

    suspend fun getUser(): User

    suspend fun deleteAll()
}