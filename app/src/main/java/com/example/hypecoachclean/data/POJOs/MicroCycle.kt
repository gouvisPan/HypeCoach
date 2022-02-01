package com.example.hypecoachclean.data.POJOs

import com.example.hypecoachclean.Constants
import java.io.Serializable

class MicroCycle(
    var workoutsPerWeek: Int,
    var primaryFocus: String,
    var secondaryFocus: String,
    var gainMuscle: Boolean,
): Serializable {

    lateinit var startingVolume: ArrayList<Int>

    var sessions = ArrayList<Session>()
    var volumeAdjustment = arrayListOf(0,0,0,0,0,0,0,0,0,0,0)
    var weekCounter = 1
    var adherencePoints = 0

    private fun addSession(ses: Session){
        sessions.add(ses)
    }

    fun printVolumeArray(){
        println("Size: ${volumeAdjustment.size}")
        for(i in volumeAdjustment){
            println(i)
        }

    }
    private fun increaseVolume(incrementValue: Int,muscle: Int) {

        when (incrementValue) {
            2, 4 -> {
                for (session in sessions) {
                    if (session.volumeList[muscle] in 1..9) {
                        session.increaseVolume(incrementValue/2, muscle)
                    }
                }

            }
            1, -1 -> {
                for (session in sessions) {
                    if (session.volumeList[muscle] in 1..9) {
                        session.increaseVolume(incrementValue, muscle)
                    }
                }
            }

        }
    }
    fun reAdjustVolumeForNextWeek(){

        for((j,i) in volumeAdjustment.withIndex()) {
            if (gainMuscle) {
                if (Constants.intToMuscle(j) == primaryFocus || Constants.intToMuscle(j) == secondaryFocus) {
                    when (i) {
                        0, 1 -> increaseVolume(4, j)
                        2, 3 -> increaseVolume(2, j)
                        4, 5 -> increaseVolume(1, j)
                        8, 9 -> increaseVolume(-1, j)
                    }

                } else {
                    when (i) {
                        0, 1 -> increaseVolume(2, j)
                        2, 3 -> increaseVolume(1, j)
                        7, 8, 9 -> increaseVolume(-1, j)
                    }
                }
            } else {
                when (i) {
                    0, 1 -> increaseVolume(2, j)
                    2, 3 -> increaseVolume(1, j)
                    8, 9 -> increaseVolume(-1, j)
                }
            }
        }
        volumeAdjustment = arrayListOf(0,0,0,0,0,0,0,0,0,0,0)
    }

    private fun validateStartingVolume(){

        if(gainMuscle) {
            if (startingVolume[Constants.CHEST_POSITION] !in 12..19) {
                startingVolume[Constants.CHEST_POSITION] = 13
            }

            if (startingVolume[Constants.BACK_POSITION] !in 13..20) {
                startingVolume[Constants.BACK_POSITION] = 14
            }

            if (startingVolume[Constants.TRICEPS_POSITION] !in 6..11) {
                startingVolume[Constants.TRICEPS_POSITION] = 6
            }

            if (startingVolume[Constants.BICEPS_POSITION] !in 8..14) {
                startingVolume[Constants.BICEPS_POSITION] = 8
            }

            if (startingVolume[Constants.QUADS_POSITION] !in 8..14) {
                startingVolume[Constants.QUADS_POSITION] = 8
            }

            if (startingVolume[Constants.HAMSTRINGS_POSITION] !in 4..10) {
                startingVolume[Constants.HAMSTRINGS_POSITION] = 8
            }

            startingVolume[Constants.FRONT_DELTS_POSITION] = 4
            startingVolume[Constants.SIDE_DELTS_POSITION] = 3
            startingVolume[Constants.REAR_DELTS_POSITION] = 3
            startingVolume[Constants.CALVES_POSITION] = 4
            startingVolume[Constants.GLUTS_POSITION] = 4
        }else{
            startingVolume[Constants.QUADS_POSITION] = 9
            startingVolume[Constants.HAMSTRINGS_POSITION] = 7
            startingVolume[Constants.CHEST_POSITION] = 10
            startingVolume[Constants.BACK_POSITION] = 10
            startingVolume[Constants.TRICEPS_POSITION] = 6
            startingVolume[Constants.BICEPS_POSITION] = 8
            startingVolume[Constants.FRONT_DELTS_POSITION] = 3
            startingVolume[Constants.SIDE_DELTS_POSITION] = 4
            startingVolume[Constants.REAR_DELTS_POSITION] = 4
            startingVolume[Constants.CALVES_POSITION] = 7
            startingVolume[Constants.GLUTS_POSITION] = 3
        }


    }
    private fun adjustVolumeToFocusedMuscleGroups() {

        when (primaryFocus) {
            "Chest" -> {
                startingVolume[Constants.CHEST_POSITION] += 2

                if(workoutsPerWeek == 4){
                    startingVolume[Constants.QUADS_POSITION] = startingVolume[Constants.QUADS_POSITION] - 3
                    startingVolume[Constants.HAMSTRINGS_POSITION]-=3
                }
            }
            "Back" -> {
                startingVolume[Constants.BACK_POSITION] += 3

                if(workoutsPerWeek == 4){
                    startingVolume[Constants.QUADS_POSITION] = startingVolume[Constants.QUADS_POSITION] - 3
                    startingVolume[Constants.HAMSTRINGS_POSITION]-=3
                }
            }
            "Legs" -> {
                startingVolume[Constants.QUADS_POSITION] += 2
                startingVolume[Constants.HAMSTRINGS_POSITION] += 2
                startingVolume[Constants.BACK_POSITION]-=2

                if(workoutsPerWeek == 4){
                    startingVolume[Constants.BACK_POSITION]-=1
                    startingVolume[Constants.CHEST_POSITION]-=1
                }

            }
        }
        when (secondaryFocus) {
            "Triceps" -> {
                startingVolume[Constants.TRICEPS_POSITION] += 2
            }
            "Biceps" -> {
                startingVolume[Constants.BICEPS_POSITION] += 2
            }
            "Delts" -> {
                startingVolume[Constants.FRONT_DELTS_POSITION] += 1
                startingVolume[Constants.SIDE_DELTS_POSITION] += 2
                startingVolume[Constants.REAR_DELTS_POSITION] += 2
            }
        }
    }
    fun generateCycle(){

        validateStartingVolume()
        if(gainMuscle) {
            adjustVolumeToFocusedMuscleGroups()
        }

        when(workoutsPerWeek) {
            4 -> {
                if (primaryFocus == "Legs" && gainMuscle) {
                    generateUpperLowerUpperLower()
                } else if(gainMuscle) {
                    generatePushPull()
                }else{
                    generatePushPullLowerUpper()
                }
            }
            5 -> {
                if (primaryFocus == "Legs" || !gainMuscle) {
                    generatePushPullLegsULSplit()
                } else {
                    generatePushPullLegsPPSplit()
                }
            }
            6 -> {
                generatePushPullLegsSplit()
            }
        }
        for(i in sessions){
            i.clearAvailables()
        }
    }
    private fun  generatePushPullLowerUpper(){

        val exerciseArray = Constants.defaultExerciseList()
        var volumeArray = initializeToZero()

        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2) + 1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.FRONT_DELTS_POSITION] = startingVolume[Constants.FRONT_DELTS_POSITION]
        volumeArray[Constants.CALVES_POSITION]=startingVolume[Constants.CALVES_POSITION]/2
        val pushSession = Session(volumeArray,exerciseArray,"Push",0L)
        this.addSession(pushSession)



        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] = (startingVolume[Constants.BACK_POSITION] / 2) + 2
        volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2) + 1
        volumeArray[Constants.REAR_DELTS_POSITION]=startingVolume[Constants.REAR_DELTS_POSITION]
        val pullSession = Session(volumeArray,exerciseArray,"Pull",1)
        this.addSession(pullSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] = startingVolume[Constants.QUADS_POSITION]
        volumeArray[Constants.HAMSTRINGS_POSITION]= startingVolume[Constants.HAMSTRINGS_POSITION]
        volumeArray[Constants.GLUTS_POSITION]=startingVolume[Constants.GLUTS_POSITION]
        volumeArray[Constants.CALVES_POSITION]=startingVolume[Constants.CALVES_POSITION]/2

        val legSession = Session(volumeArray,exerciseArray,"Legs",2)
        this.addSession(legSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2)-1
        volumeArray[Constants.BACK_POSITION]=(startingVolume[Constants.BACK_POSITION] / 2)-1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2)-1
        volumeArray[Constants.SIDE_DELTS_POSITION] = startingVolume[Constants.SIDE_DELTS_POSITION]
        val upperSession = Session(volumeArray,exerciseArray,"Upper",3)
        this.addSession(upperSession)


    }


    private fun generatePushPull(){
        val exerciseArray = Constants.defaultExerciseList()
        var volumeArray = initializeToZero()


        volumeArray[Constants.CHEST_POSITION] = startingVolume[Constants.CHEST_POSITION] / 2 - 1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2 - 1
        volumeArray[Constants.FRONT_DELTS_POSITION] = startingVolume[Constants.FRONT_DELTS_POSITION]
        volumeArray[Constants.QUADS_POSITION] = startingVolume[Constants.QUADS_POSITION]
        val pushSession = Session(volumeArray,exerciseArray,"Push 1",0)
        this.addSession(pushSession)



        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] =  startingVolume[Constants.BACK_POSITION] / 2 + 1
        volumeArray[Constants.BICEPS_POSITION]= startingVolume[Constants.BICEPS_POSITION] / 2 + 2
        volumeArray[Constants.REAR_DELTS_POSITION]=startingVolume[Constants.REAR_DELTS_POSITION]
        val pullSession = Session(volumeArray,exerciseArray,"Pull 1",1)
        this.addSession(pullSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2) + 1
        volumeArray[Constants.TRICEPS_POSITION] = (startingVolume[Constants.TRICEPS_POSITION] / 2 )+ 1
        volumeArray[Constants.SIDE_DELTS_POSITION]=startingVolume[Constants.SIDE_DELTS_POSITION] - 1
        val secondPushSession = Session(volumeArray,exerciseArray,"Push 2",2)
        this.addSession(secondPushSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] =  startingVolume[Constants.BACK_POSITION] / 2 - 2
        volumeArray[Constants.BICEPS_POSITION]= startingVolume[Constants.BICEPS_POSITION] / 2 - 1
        volumeArray[Constants.HAMSTRINGS_POSITION] = startingVolume[Constants.HAMSTRINGS_POSITION]
        volumeArray[Constants.GLUTS_POSITION] = startingVolume[Constants.GLUTS_POSITION]
        val secondPullSession = Session(volumeArray,exerciseArray,"Pull 2",3)
        this.addSession(secondPullSession)
    }


    private fun generateUpperLowerUpperLower(){
        val exerciseArray = Constants.defaultExerciseList()
        var volumeArray = initializeToZero()


        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2) - 1
        volumeArray[Constants.BACK_POSITION]=(startingVolume[Constants.BACK_POSITION] / 2) + 1
        if(secondaryFocus == "Triceps") {
            volumeArray[Constants.TRICEPS_POSITION] =
                (startingVolume[Constants.TRICEPS_POSITION] / 2) + 1
        }else{
            volumeArray[Constants.FRONT_DELTS_POSITION] = startingVolume[Constants.FRONT_DELTS_POSITION]-1
        }

        volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2) - 1
        val upperSession = Session(volumeArray,exerciseArray,"Upper 1",0)
        this.addSession(upperSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] = (startingVolume[Constants.QUADS_POSITION] / 2) + 1
        volumeArray[Constants.HAMSTRINGS_POSITION]= (startingVolume[Constants.HAMSTRINGS_POSITION] / 2) - 1
        volumeArray[Constants.GLUTS_POSITION]=startingVolume[Constants.GLUTS_POSITION]
        val lowerSession = Session(volumeArray,exerciseArray, "Lower 1",1)
        this.addSession(lowerSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.CHEST_POSITION] = startingVolume[Constants.CHEST_POSITION] / 2
        volumeArray[Constants.BACK_POSITION]=(startingVolume[Constants.BACK_POSITION] / 2) -1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        if(secondaryFocus == "Biceps") {
            volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2) + 1
        }else{
            volumeArray[Constants.REAR_DELTS_POSITION] = startingVolume[Constants.REAR_DELTS_POSITION]-1
        }

        val secondUpperSession = Session(volumeArray,exerciseArray,"Upper 2",2)
        this.addSession(secondUpperSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] = (startingVolume[Constants.QUADS_POSITION] / 2) - 1
        volumeArray[Constants.HAMSTRINGS_POSITION]= (startingVolume[Constants.HAMSTRINGS_POSITION] / 2) + 1
        volumeArray[Constants.CALVES_POSITION]=startingVolume[Constants.CALVES_POSITION]
        val secondLowerSession = Session(volumeArray,exerciseArray,"Lower 2",3)
        this.addSession(secondLowerSession)
    }

    private fun generatePushPullLegsSplit(){
        val exerciseArray = Constants.defaultExerciseList()
        var volumeArray = initializeToZero()

        volumeArray[Constants.CHEST_POSITION] = startingVolume[Constants.CHEST_POSITION] / 2
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.FRONT_DELTS_POSITION] = startingVolume[Constants.FRONT_DELTS_POSITION]
        val pushSession = Session(volumeArray,exerciseArray,"Push 1",0)
        this.addSession(pushSession)



        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] =  startingVolume[Constants.BACK_POSITION] / 2
        volumeArray[Constants.BICEPS_POSITION]= (startingVolume[Constants.BICEPS_POSITION] / 2) - 1
        volumeArray[Constants.REAR_DELTS_POSITION]=startingVolume[Constants.REAR_DELTS_POSITION]
        val pullSession = Session(volumeArray,exerciseArray,"Pull 1",1)
        this.addSession(pullSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] =     startingVolume[Constants.QUADS_POSITION]/2
        volumeArray[Constants.HAMSTRINGS_POSITION]= startingVolume[Constants.HAMSTRINGS_POSITION]/2
        volumeArray[Constants.GLUTS_POSITION]=     startingVolume[Constants.GLUTS_POSITION] + 1
        val legSession = Session(volumeArray,exerciseArray,"Legs 1",2)
        this.addSession(legSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2)+1
        volumeArray[Constants.TRICEPS_POSITION] = (startingVolume[Constants.TRICEPS_POSITION] / 2) + 1
        val secondPushSession = Session(volumeArray,exerciseArray,"Push 2",3)
        this.addSession(secondPushSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] =  (startingVolume[Constants.BACK_POSITION] / 2) + 2
        volumeArray[Constants.BICEPS_POSITION]= (startingVolume[Constants.BICEPS_POSITION] / 2) + 1
        volumeArray[Constants.REAR_DELTS_POSITION]=startingVolume[Constants.REAR_DELTS_POSITION] - 1
        volumeArray[Constants.SIDE_DELTS_POSITION] = startingVolume[Constants.SIDE_DELTS_POSITION] -1
        val secondPullSession = Session(volumeArray,exerciseArray,"Pull 2",4)
        this.addSession(secondPullSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] =     startingVolume[Constants.QUADS_POSITION] / 2
        volumeArray[Constants.HAMSTRINGS_POSITION]= startingVolume[Constants.HAMSTRINGS_POSITION] / 2
        volumeArray[Constants.CALVES_POSITION] =     startingVolume[Constants.CALVES_POSITION]
        val secondLegSession = Session(volumeArray,exerciseArray,"Legs 2",5)
        this.addSession(secondLegSession)


    }
    private fun generatePushPullLegsPPSplit() {

        val exerciseArray = Constants.defaultExerciseList()
        var volumeArray = initializeToZero()

        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2) + 1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.FRONT_DELTS_POSITION] = startingVolume[Constants.FRONT_DELTS_POSITION]
        val pushSession = Session(volumeArray,exerciseArray,"Push 1",0)
        this.addSession(pushSession)



        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] =  startingVolume[Constants.BACK_POSITION] / 2
        volumeArray[Constants.BICEPS_POSITION]= startingVolume[Constants.BICEPS_POSITION] / 2
        volumeArray[Constants.REAR_DELTS_POSITION]=startingVolume[Constants.REAR_DELTS_POSITION]
        volumeArray[Constants.SIDE_DELTS_POSITION]=startingVolume[Constants.SIDE_DELTS_POSITION] - 1
        val pullSession = Session(volumeArray,exerciseArray,"Pull 1",1)
        this.addSession(pullSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] =     startingVolume[Constants.QUADS_POSITION]
        volumeArray[Constants.HAMSTRINGS_POSITION]= startingVolume[Constants.HAMSTRINGS_POSITION]/2
        volumeArray[Constants.GLUTS_POSITION]=     startingVolume[Constants.GLUTS_POSITION]
        val legSession = Session(volumeArray,exerciseArray,"Legs",2)
        this.addSession(legSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2)
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.SIDE_DELTS_POSITION] = (startingVolume[Constants.SIDE_DELTS_POSITION]/2) + 2
        val secondPushSession = Session(volumeArray,exerciseArray,"Push 2",3)
        this.addSession(secondPushSession)

        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] = (startingVolume[Constants.BACK_POSITION] / 2) + 1
        volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2) + 1
        volumeArray[Constants.HAMSTRINGS_POSITION] = startingVolume[Constants.HAMSTRINGS_POSITION] / 2
        val secondPullSession = Session(volumeArray,exerciseArray,"Pull 2",4)
        this.addSession(secondPullSession)

    }



    private fun generatePushPullLegsULSplit(){
        val exerciseArray = Constants.defaultExerciseList()
        var volumeArray = initializeToZero()

        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2) + 1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.FRONT_DELTS_POSITION] = startingVolume[Constants.FRONT_DELTS_POSITION]
        val pushSession = Session(volumeArray,exerciseArray,"Push",0)
        this.addSession(pushSession)



        volumeArray = initializeToZero()
        volumeArray[Constants.BACK_POSITION] = (startingVolume[Constants.BACK_POSITION] / 2) + 2
        volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2) + 1
        volumeArray[Constants.REAR_DELTS_POSITION]=startingVolume[Constants.REAR_DELTS_POSITION]
        val pullSession = Session(volumeArray,exerciseArray,"Pull",1)
        this.addSession(pullSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] = (startingVolume[Constants.QUADS_POSITION] / 2) -1
        volumeArray[Constants.HAMSTRINGS_POSITION]= (startingVolume[Constants.HAMSTRINGS_POSITION] / 2) + 1
        volumeArray[Constants.GLUTS_POSITION]=startingVolume[Constants.GLUTS_POSITION]
        val legSession = Session(volumeArray,exerciseArray,"Legs",2)
        this.addSession(legSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.CHEST_POSITION] = (startingVolume[Constants.CHEST_POSITION] / 2)-1
        volumeArray[Constants.BACK_POSITION]=(startingVolume[Constants.BACK_POSITION] / 2) -1
        volumeArray[Constants.TRICEPS_POSITION] = startingVolume[Constants.TRICEPS_POSITION] / 2
        volumeArray[Constants.BICEPS_POSITION]=(startingVolume[Constants.BICEPS_POSITION] / 2) - 1
        volumeArray[Constants.SIDE_DELTS_POSITION] = startingVolume[Constants.SIDE_DELTS_POSITION]
        val upperSession = Session(volumeArray,exerciseArray,"Upper",3)
        this.addSession(upperSession)


        volumeArray = initializeToZero()
        volumeArray[Constants.QUADS_POSITION] = (startingVolume[Constants.QUADS_POSITION] / 2) + 1
        volumeArray[Constants.HAMSTRINGS_POSITION]= (startingVolume[Constants.HAMSTRINGS_POSITION] / 2)-1
        volumeArray[Constants.CALVES_POSITION]=startingVolume[Constants.CALVES_POSITION]
        val lowerSession = Session(volumeArray,exerciseArray,"Lower",4)
        this.addSession(lowerSession)


    }

    fun printMicro(){
        println("MICRO INFORMATION : ")
        println("Primary Focus : $primaryFocus ")
        println("Secondary Focus : $secondaryFocus ")

        println("Session number: ${sessions.size}")
        println("The weekly program: ")
        for ((j, i) in sessions.withIndex()){
            println("Session NO $j")
            i.print()
            println("")
        }
    }

    private fun initializeToZero(): ArrayList<Int>{
        return arrayListOf(0,0,0,0,0,0,0,0,0,0,0)
    }
}