package com.example.hypecoachclean.presentation.Main.WorkingOut

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.POJOs.ExerciseInSession
import com.example.hypecoachclean.data.POJOs.MicroCycle
import com.example.hypecoachclean.data.POJOs.Set
import com.example.hypecoachclean.objectToString
import com.example.hypecoachclean.repository.UserRepository
import com.example.hypecoachclean.stringToObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkingOutViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)

    lateinit var  microCycle : MicroCycle

    //Timer Vars
    private lateinit var exTimer: CountDownTimer
    private var restTimeout : Long = 60
    private var timerTime = 0
    val timeCount = MutableLiveData<Int>()

    //WorkingOutFragment LiveDataVars
    val microL = MutableLiveData<MicroCycle>()
    val hExerciseL = MutableLiveData<ExerciseInSession>()
    val cExerciseL = MutableLiveData<ExerciseInSession>()
    val positionL = MutableLiveData<Int>()
    val lastExerciseL = MutableLiveData(false)
    val workoutSavedL = MutableLiveData(false)







    private lateinit var historyExercises :ArrayList<ExerciseInSession>
    private lateinit var currentExercises :ArrayList<ExerciseInSession>

    var exerciseNum = 0
    var sessionNum = 0

    fun getExercise(theSession: Int){
        sessionNum = theSession
        viewModelScope.launch(Dispatchers.IO){
            val program = getProgram()
            historyExercises= stringToObject(program).sessions[theSession].exercises
            currentExercises= stringToObject(program).sessions[theSession].exercises

            for(exercise in currentExercises){
                exercise.getProposedSets()
            }

            hExerciseL.postValue(historyExercises[exerciseNum])
            cExerciseL.postValue(currentExercises[exerciseNum])
            positionL.postValue(0)
        }
    }

    fun nextExercise(sets: ArrayList<Set>){

        if(exerciseNum == currentExercises.size - 2){
            lastExerciseL.postValue(true)
        }

        if(exerciseNum == currentExercises.size - 1){
            currentExercises[exerciseNum].setSets(sets)
            saveWorkout()

        }else{

            currentExercises[exerciseNum].setSets(sets)
            exerciseNum++

            hExerciseL.postValue(historyExercises[exerciseNum])
            cExerciseL.postValue(currentExercises[exerciseNum])
            positionL.postValue(exerciseNum)
        }

    }

    fun prevExercise(){
        if(exerciseNum == currentExercises.size - 1) {
            lastExerciseL.postValue(false)
        }

        exerciseNum--

        hExerciseL.postValue(historyExercises[exerciseNum])
        cExerciseL.postValue(currentExercises[exerciseNum])
        positionL.postValue(exerciseNum)
    }

    private fun saveWorkout(){
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()
            val micro = stringToObject(user.program)
            val myHashMap = HashMap<String,Any>()

            micro.sessions[sessionNum].exercises = currentExercises
            user.program = objectToString(micro)

            userRepository.addUser(user)

            myHashMap[Constants.PROGRAM] =  user.program
            userRepository.updateRemote(myHashMap)

            workoutSavedL.postValue(true)
        }
    }

    private suspend fun getProgram(): String {
        return userRepository.getUser().program
    }


    fun startTimer() {
        timerTime =historyExercises[exerciseNum].getExercise().getRest()

        exTimer = object : CountDownTimer(restTimeout*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTime--
                timeCount.postValue(timerTime)
            }
            override fun onFinish() {
                timerTime =historyExercises[exerciseNum].getExercise().getRest()
                timeCount.postValue(timerTime)
            }
        }.start()
    }

    fun stopTimer(){
        exTimer.cancel()
        timerTime =historyExercises[exerciseNum].getExercise().getRest()
        timeCount.postValue(timerTime)
    }
    fun add30(){
        timerTime += 30
    }



}