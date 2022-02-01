package com.example.hypecoachclean.presentation.base

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hypecoachclean.presentation.Main.ChallengeAndPumpInput.ChallengeAndPumpViewModel
import com.example.hypecoachclean.presentation.Main.MainViewModel
import com.example.hypecoachclean.presentation.Main.CreateProgram.CreateProgramViewModel
import com.example.hypecoachclean.presentation.Main.DIsruptionInput.DisruptionInputViewModel
import com.example.hypecoachclean.presentation.Main.Macros.MacrosViewModel
import com.example.hypecoachclean.presentation.Main.Profile.ProfileViewModel
import com.example.hypecoachclean.presentation.Main.WeeklyProgram.WeeklyProgramViewModel
import com.example.hypecoachclean.presentation.Main.WeightLog.WeightLogViewModel
import com.example.hypecoachclean.presentation.Main.WorkingOut.WorkingOutViewModel
import com.example.hypecoachclean.presentation.auth.AuthViewModel
import com.firebase.ui.auth.AuthUI.getApplicationContext
import java.lang.IllegalArgumentException

class ViewModelFactory(): ViewModelProvider.NewInstanceFactory() {

    @SuppressLint("RestrictedApi")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java)-> AuthViewModel() as T
            modelClass.isAssignableFrom(MainViewModel::class.java)-> MainViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(WeeklyProgramViewModel::class.java)-> WeeklyProgramViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(WorkingOutViewModel::class.java)-> WorkingOutViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(CreateProgramViewModel::class.java)-> CreateProgramViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java)-> ProfileViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(WeightLogViewModel::class.java)-> WeightLogViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(MacrosViewModel::class.java)-> MacrosViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(DisruptionInputViewModel::class.java)-> DisruptionInputViewModel(getApplicationContext()) as T
            modelClass.isAssignableFrom(ChallengeAndPumpViewModel::class.java)-> ChallengeAndPumpViewModel(getApplicationContext()) as T


            else-> throw IllegalArgumentException("ViewModelClass not Found!!!")
        }
    }
}