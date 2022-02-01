package com.example.hypecoachclean.data

import android.content.Context
import com.example.hypecoachclean.data.POJOs.User
import com.example.hypecoachclean.data.POJOs.DataSource
import com.example.hypecoachclean.data.db.UserDatabase
import com.example.hypecoachclean.data.db.UserEntity

class RoomUserDataSource(context: Context): DataSource {
    val userDao = UserDatabase.getDatabase(context).userDao()

    override suspend fun addUser(user: User) = userDao.addUserEntity(UserEntity.fromUser(user))

    override suspend fun delete(user: User) = userDao.deleteUserEntity(UserEntity.fromUser(user))

    override suspend fun getUser() = userDao.getUserEntity().toUser()

    override suspend fun deleteAll() = userDao.deleteAll()
}