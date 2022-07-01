package com.example.hypecoachclean.presentation.Main.WorkingOut

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.data.BusinessLogic.ExerciseInSession
import com.example.hypecoachclean.data.BusinessLogic.MicroCycle
import com.example.hypecoachclean.data.BusinessLogic.Set
import com.example.hypecoachclean.data.BusinessLogic.User
import com.example.hypecoachclean.objectToString
import com.example.hypecoachclean.repository.UserRepository
import com.example.hypecoachclean.stringToObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkingOutViewModel(context: Context): ViewModel() {

    private var  userRepository= UserRepository(context)

    lateinit var  microCycle : MicroCycle
    lateinit var user : User

    //Timer Vars
    private lateinit var exTimer: CountDownTimer
    private var restTimeout : Long = 60
    private var timerTime = 0
    val timeCount = MutableLiveData<Int>()
    private var isTimerRunning = false

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
            user = userRepository.getUser()

            microCycle = stringToObject(user.program)
            historyExercises= stringToObject(user.program).sessions[theSession].exercises
            currentExercises= stringToObject(user.program).sessions[theSession].exercises

            for(exercise in currentExercises){
                exercise.getProposedSets()
            }

            hExerciseL.postValue(historyExercises[exerciseNum])
            cExerciseL.postValue(currentExercises[exerciseNum])
            positionL.postValue(0)
        }
    }

    fun nextExercise(sets: ArrayList<Set>){
        if(isTimerRunning){
            stopTimer()
        }

        if(exerciseNum == currentExercises.size - 2){
            lastExerciseL.postValue(true)
        }

        if(exerciseNum == currentExercises.size - 1){
            currentExercises[exerciseNum].setSets(sets)
            microCycle.sessions[sessionNum].exercises = currentExercises
            repaintWeeklyList()
            manageAdherance()
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
        if(isTimerRunning){
            stopTimer()
        }
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
            val myHashMap = HashMap<String,Any>()

            user.program = objectToString(microCycle)

            userRepository.deleteAll()
            userRepository.addUser(user)

            myHashMap[Constants.PROGRAM] =  user.program
            myHashMap[Constants.ADHERENCE] = user.adherence
            userRepository.updateRemote(myHashMap)

            workoutSavedL.postValue(true)
        }
    }

    fun startTimer() {
        timerTime =historyExercises[exerciseNum].getExercise().getRest()
        isTimerRunning = true
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
        isTimerRunning = false
        exTimer.cancel()
        timerTime =historyExercises[exerciseNum].getExercise().getRest()
        timeCount.postValue(timerTime)
    }
    fun add30(){
        timerTime += 30
    }

    private fun manageAdherance(){
        if(sessionNum == 0){
            user.adherence = 20
        }else {
            user.adherence = user.adherence + 20
        }
    }

    private fun repaintWeeklyList(){
        microCycle.sessions[sessionNum].done(true)

        if(sessionNum == microCycle.sessions.size - 1){
            for(i in microCycle.sessions) {
                i.done(false)
            }
        }
    }
}
















