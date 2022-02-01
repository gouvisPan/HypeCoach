package com.example.hypecoachclean.data.POJOs

interface DataSource {

    suspend fun addUser(user: User)

    suspend fun delete(user: User)

    suspend fun getUser(): User

    suspend fun deleteAll()
}