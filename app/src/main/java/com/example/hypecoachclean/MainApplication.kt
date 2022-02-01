package com.example.hypecoachclean


import android.app.Application
import android.content.Context
import com.example.hypecoachclean.data.RoomUserDataSource

class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }


    }


    override fun onCreate() {
        super.onCreate()

        val context: Context =applicationContext()

    }
}