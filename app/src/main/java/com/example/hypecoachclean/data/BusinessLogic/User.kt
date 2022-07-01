package com.example.hypecoachclean.data.BusinessLogic

data class User(
    var id:String="",
    var name: String="",
    var email: String= "",
    var birthString: String="",
    var genderString: String="",
    var image: String = "",
    var adherence: Int= 0,
    var log: String="",
    var fcmToken: String="",
    var height: String="",
    var program: String="",
    var macros: ArrayList<Int> = arrayListOf(0,0,0,0,0)
)