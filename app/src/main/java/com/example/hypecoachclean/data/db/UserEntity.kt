package com.example.hypecoachclean.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.hypecoachclean.data.POJOs.User
import com.example.hypecoachclean.data.POJOs.Weight
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "user_table")
data class UserEntity (
        var name: String,
        var email: String,
        var birthString: String="",
        var genderString: String="",
        var image: String = "",
        var adherence: Int= 0,
        var log: String="",
        var fcmToken:String="",
        var height: String="",
        var program: String="",
        var macros: ArrayList<Int> = arrayListOf(0,0,0,0,0),
        @PrimaryKey(autoGenerate = true)
        var id: Int= 0
    ){
    companion object{
        fun fromUser(user: User) = UserEntity(
            user.name,
            user.email,
            user.birthString,
            user.genderString,
            user.image,
            user.adherence,
            user.log,
            user.fcmToken,
            user.height,
            user.program,
            user.macros
        )
    }

    fun toUser() = User(id.toString(),name,email,birthString,genderString,image,adherence,log,fcmToken,height,program,macros)
}

class MacrosTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<Int>{
        val listType = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun fromArrayList(array: ArrayList<Int>):String{
        return Gson().toJson(array)
    }

}