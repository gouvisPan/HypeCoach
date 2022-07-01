package com.example.hypecoachclean


import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.hypecoachclean.data.BusinessLogic.MicroCycle
import com.example.hypecoachclean.data.BusinessLogic.Set
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList
import kotlin.math.round


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun stringToObject(program : String): MicroCycle {
    val microCycle = Gson().fromJson(program, MicroCycle::class.java)
    return microCycle
}

fun objectToString(microCycle: MicroCycle): String{
    var microString = Gson().toJson(microCycle)
    return  microString
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorBody: String
    ): Resource<Nothing>()
}

fun cloneSetsWithDifferentReference(list : ArrayList<Set>) : ArrayList<Set>{
    var otherReferenceArrayString = Gson().toJson(list)
    val typeToken = object : TypeToken<ArrayList<Set>>() {}.type
    val otherReferenceArray = Gson().fromJson<ArrayList<Set>>(otherReferenceArrayString, typeToken)

   return otherReferenceArray
}





