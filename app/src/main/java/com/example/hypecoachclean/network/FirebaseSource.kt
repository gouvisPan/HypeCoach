package com.example.hypecoachclean.network

import android.net.Uri
import android.util.Log
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.POJOs.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class FirebaseSource {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()

    }

    private val myFireStore = FirebaseFirestore.getInstance()


    suspend fun login(email: String, password: String) : FirebaseUser? {

        return try {
            val data = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            Log.d("Sign in", "signInWithEmail:success")
            data.user

        }catch (e : Exception){
            Log.w("Sign in", "signInWithEmail:failure")
            null
        }

    }

    suspend fun register(name: String, email: String,password: String): FirebaseUser?{

        return try {
            val data = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()

            Log.d("Register", "RegisterWithEmail:success")
            var user = User(data.user!!.uid,name,email,
                "","","",0,
                "","","","", arrayListOf(0,0,0,0,0))

            storeUser(user)
            login(email,password)  //logging in the user automatically with the registration

            data.user


        }catch (e : Exception){
            Log.w("Register", "RegisterWithEmail:failure")
            null
        }
    }

    private suspend fun storeUser(userInfo: User){
        try {
            myFireStore.collection(Constants.USERS)
                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                .set(userInfo, SetOptions.merge())
                .await()

        }catch (e : Exception) {
            Log.w("Name Storing Error", "signInWithEmail:failure")
        }
    }

    suspend fun loadUserData() =
        myFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).get()
            .await()!!


    suspend fun updateUser(userHashMap: HashMap<String,Any>) {
        try {
            myFireStore.collection(Constants.USERS)
                .document(getCurrentUserId())
                .update(userHashMap)
                .await()

        } catch (e: Exception) {
            Log.w("User Update", "UserHashmapUpdate:failure")
        }
    }

    suspend fun updateUserArray(userHashMap: HashMap<String,ArrayList<Int>>) {
        try {
            myFireStore.collection(Constants.USERS)
                .document(getCurrentUserId())
                .update(userHashMap as Map<String, Any>)
                .await()

        } catch (e: Exception) {
            Log.w("User Array Update", "UserHashmapUpdate:failure")
        }
    }

    suspend fun uploadImage(name: String, uri: Uri): String{
        var imgUrl = ""

        val sRef: StorageReference =
            FirebaseStorage.getInstance().reference.child(name)
        try{
            sRef.putFile(uri).await()
            Log.w("User Image Update", "UserImageUpdate:Success")
            imgUrl= sRef.downloadUrl.toString()


        }catch (e: Exception) {
            Log.w("User Image Update", "UserImageUpdate:failure")
        }

        return imgUrl
    }

    fun autoLogin():Boolean{
        return getCurrentUserId().isNotEmpty()
    }



    fun currentUser() = firebaseAuth.currentUser

    private fun getCurrentUserId(): String {

        val currentUser = firebaseAuth.currentUser
        var currentUserID = ""
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }



}