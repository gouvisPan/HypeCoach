package com.example.hypecoachclean.repository

import android.content.Context
import android.net.Uri
import com.example.hypecoachclean.data.POJOs.MicroCycle
import com.example.hypecoachclean.data.POJOs.User
import com.example.hypecoachclean.data.RoomUserDataSource
import com.example.hypecoachclean.network.FirebaseSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(context: Context): BaseRepository(){

    private val remoteDataSource = FirebaseSource()
    private val dataSource= RoomUserDataSource(context)


    suspend fun addUser(user: User){
        dataSource.addUser(user)
    }

    suspend fun updateRemote(userHashMap: HashMap<String,Any>){
        remoteDataSource.updateUser(userHashMap)
    }

    suspend fun updateRemoteArray(userHashMap: HashMap<String,ArrayList<Int>>){
        remoteDataSource.updateUserArray(userHashMap)
    }

    suspend fun delete(user: User){
        dataSource.delete(user)
    }

    suspend fun getUser():User{
       return dataSource.getUser()
    }

    //TODO Resource wrapping
    suspend fun loadUserData(){
        withContext(Dispatchers.IO) {
            val user = remoteDataSource.loadUserData().toObject(User::class.java)!!
            dataSource.deleteAll()
            dataSource.addUser(user)
        }
    }

    suspend fun uploadImage(name: String, uri: Uri): String{
        var url = remoteDataSource.uploadImage(name,uri)
        return url
    }

    suspend fun logout(){
        withContext(Dispatchers.IO) {
            FirebaseAuth.getInstance().signOut()
        }
    }
}