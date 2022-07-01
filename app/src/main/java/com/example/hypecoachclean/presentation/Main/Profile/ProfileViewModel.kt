package com.example.hypecoachclean.presentation.Main.Profile

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.BusinessLogic.User
import com.example.hypecoachclean.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(context: Context): ViewModel() {
    private var  userRepository= UserRepository(context)
    var theContext = context
    val user = MutableLiveData<User>()
    val savedL = MutableLiveData<Boolean>()
    val url = MutableLiveData<String>()

    fun getUser(){
        viewModelScope.launch(Dispatchers.IO){
            val mUser = userRepository.getUser()
            user.postValue(mUser)
        }
    }

    fun updateIfNecessary(name: String,height: String,age: String,gender: String,imageURI: Uri?){
        val myUser = user.value
        var areChangesMade = false

        if (imageURI != null) uploadImage(getName(imageURI),imageURI)

        if(name != myUser!!.name){
            myUser.name = name
            areChangesMade = true
        }
        if(height != myUser.height){
            myUser.height = height
            areChangesMade = true
        }
        if(age != myUser.birthString){
            myUser.birthString = age
            areChangesMade = true
        }
        if(gender != myUser.genderString){
            myUser.genderString = gender
            areChangesMade = true
        }
        if(areChangesMade) updateUser(myUser)
    }

    fun uploadImage(
         name: String,
         uri: Uri
    ) {
        viewModelScope.launch(Dispatchers.IO) {
           url.postValue( userRepository.uploadImage(name, uri))
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            val myHashMap = HashMap<String,Any>()
            userRepository.addUser(user)

            myHashMap[Constants.NAME] =  user.name
            myHashMap[Constants.HEIGHT] =  user.height
            myHashMap[Constants.BIRTHSTRING] =  user.birthString
            myHashMap[Constants.GENDERSTRING] = user.genderString
            myHashMap[Constants.IMAGE] = user.image
            userRepository.updateRemote(myHashMap)
            savedL.postValue(true)
        }
    }

    private fun getName(uri: Uri): String{
        return "USER_IMAGE" + System.currentTimeMillis() + "." + getFileExtension(theContext,uri)
    }

    private fun getFileExtension(context: Context,uri: Uri?) :String?{
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(context.contentResolver.getType(uri!!))
    }
}