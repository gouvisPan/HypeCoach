package com.example.hypecoachclean.presentation.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hypecoachclean.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

       // setupActionBarWithNavController(navHostFragment!!.findNavController())


    }

    fun Fragment.makeToast(text: String, duration: Int = Toast.LENGTH_LONG) {
        activity?.let {
            Toast.makeText(it, text, duration).show()
        }
    }
}