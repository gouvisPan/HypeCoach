package com.example.hypecoachclean

import com.example.hypecoachclean.data.BusinessLogic.Exercise

class Constants {

    companion object {

        const val EXTRA_SESSION = "0"
        const val EXTRA_JSON_STRING = "1"

        const val USERS = "Users"

        const val NAME = "name"
        const val IMAGE = "image"
        const val HEIGHT = "height"
        const val PROGRAM = "program"
        const val FCM = "fcmToken"
        const val BIRTHSTRING = "birthString"
        const val GENDERSTRING = "genderString"
        const val WEIGHTLOG = "log"
        const val ADHERENCE= "adherence"
        const val MACROS="macros"

        const val CARBS_POSITION = 0
        const val PROTEIN_POSITION = 1
        const val FATS_POSITION = 2
        const val GOAL_POSITION = 3
        const val MAINT_POSITION = 4

        const val QUADS_POSITION = 0
        const val HAMSTRINGS_POSITION = 1
        const val CHEST_POSITION = 2
        const val BACK_POSITION = 3
        const val TRICEPS_POSITION = 4
        const val BICEPS_POSITION = 5
        const val FRONT_DELTS_POSITION = 6
        const val REAR_DELTS_POSITION = 7
        const val SIDE_DELTS_POSITION = 8
        const val CALVES_POSITION = 9
        const val GLUTS_POSITION = 10


        fun intToMuscle(integer: Int): String{
            var myString = ""
                when(integer){
                    0-> myString = "Quads"
                    1-> myString = "Hamstrings"
                    2-> myString = "Chest"
                    3-> myString = "Back"
                    4-> myString = "Triceps"
                    5-> myString = "Biceps"
                    6-> myString = "Front Delts"
                    7-> myString = "Rear Delts"
                    8-> myString = "Side Delts"
                    9-> myString = "Calves"
                    10-> myString = "Gluts"



                }
            return myString
        }

        fun testExerciseList(): ArrayList<Exercise> {
            val tmpExercisesList = ArrayList<Exercise>()

            val benchPress =
                Exercise(5, "Horizontal Bench-Press", 1, true, 120,CHEST_POSITION,5,12,"A",false)
            tmpExercisesList.add(benchPress)

            val machinePress =
                Exercise(5, "Horizontal Chest-Press Machine", 1, true, 120,CHEST_POSITION,5,14,"A",false)
            tmpExercisesList.add(machinePress)

            val inclineBenchPress =
                Exercise(6, "Incline Bench-Press", 1, true, 120,CHEST_POSITION,5,12,"B",false)
            tmpExercisesList.add(inclineBenchPress)

            val machineInclinePress =
                Exercise(6, "Incline Chest-Press Machine", 1, true, 120,CHEST_POSITION,5,14,"B",false)
            tmpExercisesList.add(machineInclinePress)

            return  tmpExercisesList
        }



        fun defaultExerciseList(): ArrayList<Exercise> {

            val allExercisesList = ArrayList<Exercise>()

            /*val pushUp =
                Exercise(1, "Push-Ups", 1, 2, 90,"Mid_chest","Triceps","A")
            allExercisesList.add(pushUp)

            val tricepPushUp =
                Exercise(2, "Tricep Push-Ups", 1, 2, 90,"Triceps","Mid_chest","A")
            allExercisesList.add(tricepPushUp)

            val widePushUp =
                Exercise(3, "Wide Push-Ups", 1, 2, 90,"Mid_chest","Front_Delts","Push")
            allExercisesList.add(widePushUp)*/

            // --------------------------------------------CHEST EXERCISES ---------------------------------------------

            val benchPress =
                Exercise(1, "Horizontal Bench-Press", 1, true, 120, CHEST_POSITION,5,12,"A",false)
            allExercisesList.add(benchPress)

            val machinePress =
                Exercise(2, "Horizontal Chest-Press Machine", 1, true, 120,CHEST_POSITION,5,14,"A",false)
            allExercisesList.add(machinePress)

            val inclineBenchPress =
                Exercise(3, "Incline Bench-Press", 1, true, 120,CHEST_POSITION,5,12,"B",false)
            allExercisesList.add(inclineBenchPress)

            val machineInclinePress =
                Exercise(4, "Incline Chest-Press Machine", 1, true, 120,CHEST_POSITION,5,14,"B",false)
            allExercisesList.add(machineInclinePress)

            val inclineFlies =
                Exercise(5, "Incline flies", 1, false, 90,CHEST_POSITION,10,20,"B",false)
            allExercisesList.add(inclineFlies)

            val flies =
                Exercise(6, "Flies", 1, false, 90,CHEST_POSITION,10,20,"B",false)
            allExercisesList.add(flies)

            val pecDeck =
                Exercise(6, "Peck - Deck", 1, false, 90,CHEST_POSITION,10,20,"B",false)
            allExercisesList.add(pecDeck)

            // --------------------------------------------BACK EXERCISES ---------------------------------------------
            val chinUps =
                Exercise(7, "Chin-Ups", 1, true, 120, BACK_POSITION,5,12,"B",false)
            allExercisesList.add(chinUps)

            val barbelPendRows =
                Exercise(8, "Pendlay Rows", 1, true, 120,BACK_POSITION,5,12,"A",false)
            allExercisesList.add(barbelPendRows)

            val upperBackMachine =
                Exercise(9, "Row Machine", 1, true, 120,BACK_POSITION,8,12,"A",false)
            allExercisesList.add(upperBackMachine)

           val latPullDown =
               Exercise(10, "Cable Lat Pulldown", 1, false, 120,BACK_POSITION,10,16,"B",false)
            allExercisesList.add(latPullDown)

            val latPullOver =
                Exercise(11, "Cable Lat Pullover", 1, false, 120,BACK_POSITION,5,14,"B",false)
            allExercisesList.add(latPullOver)


            // --------------------------------------------BICEPS EXERCISES ---------------------------------------------
            val barbelCurls =
                Exercise(12, "Standing Barbel Curls", 1, true, 120, BICEPS_POSITION,8,12,"A",false)
            allExercisesList.add(barbelCurls)

            val twistDumbbellCurls =
                Exercise(13, "Twisting Dumbbell Curls", 1, true, 90, BICEPS_POSITION,10,18,"B",false)
            allExercisesList.add(twistDumbbellCurls)

            val dumbbellCurls =
                Exercise(14, "Twisting Dumbbell Curls", 1, true, 90, BICEPS_POSITION,10,18,"A",false)
            allExercisesList.add(dumbbellCurls)


            val cableRopeCurls =
                Exercise(15, "Rope Bicep Curls", 1, false, 90, BICEPS_POSITION,10,20,"B",false)
            allExercisesList.add(cableRopeCurls)


            // --------------------------------------------TRICEPS EXERCISES ---------------------------------------------
            val tricepsPushDown =
                Exercise(16, "Triceps pushdown", 1, true, 90, TRICEPS_POSITION,10,20,"A",false)
            allExercisesList.add(tricepsPushDown)

            val tricepsBarPress =
                Exercise(17, "Triceps bar press", 1, true, 120, TRICEPS_POSITION,6,12,"A",false)
            allExercisesList.add(tricepsBarPress)

            val tricepsOverheadRopeExtension =
                Exercise(18, "Tricep OverHead Rope Extension", 1, false, 90, TRICEPS_POSITION,10,16,"B",false)
            allExercisesList.add(tricepsOverheadRopeExtension)

            val scullCrusher=
                Exercise(19, "Skull Crashers", 1, true, 90, TRICEPS_POSITION,8,12,"B",false)
            allExercisesList.add(scullCrusher)


            // --------------------------------------------QUAD EXERCISES ---------------------------------------------
            val barbelSquat =
                Exercise(20, "Squat", 1, true, 180, QUADS_POSITION,5,12,"A",false)
            allExercisesList.add(barbelSquat)

            val LegPressDeep  =
                Exercise(21, "LegPress", 1, true, 120,QUADS_POSITION,8,16,"A",false)
            allExercisesList.add(LegPressDeep)

            val lunges =
                Exercise(22, "Lunges", 1, true, 120,QUADS_POSITION,8,14,"A",false)
            allExercisesList.add(lunges)

            val legExtension =
                Exercise(23, "Leg extensions", 1, false, 90,QUADS_POSITION,10,20,"B",false)
            allExercisesList.add(legExtension)



            // --------------------------------------------HAMSTRING EXERCISES ---------------------------------------------
            val sumoDeadlifts =
                Exercise(24, "Sumo Deadlifts", 1, true, 180, HAMSTRINGS_POSITION,5,10,"A",false)
            allExercisesList.add(sumoDeadlifts)

            val romanianDL  =
                Exercise(25, "Romanian DL", 1, true, 180,HAMSTRINGS_POSITION,5,10,"A",false)
            allExercisesList.add(romanianDL)

            val legCurls =
                Exercise(26, "Leg curls", 1, false, 90,HAMSTRINGS_POSITION,10,20,"B",false)
            allExercisesList.add(legCurls)


            // --------------------------------------------GLUTE EXERCISES ---------------------------------------------
            val HipThrust =
                Exercise(27, "Hip Thrusters", 1, true, 60, GLUTS_POSITION,5,12,"A",false)
            allExercisesList.add(HipThrust)

            val oneLegHipThrust =
                Exercise(28, "One Leg Hip Thrust", 1, true, 60, GLUTS_POSITION,10,20,"A",false)
            allExercisesList.add(oneLegHipThrust)



            // --------------------------------------------CALVE EXERCISES ---------------------------------------------

            val calveRaiseMachine =
                Exercise(29, "Calve Raise Machine", 1, true, 60, CALVES_POSITION,8,20,"A",false)
            allExercisesList.add(calveRaiseMachine)

            val calveRaiseSmith =
                Exercise(29, "Calve Raise Machine", 1, true, 60, CALVES_POSITION,8,20,"A",false)
            allExercisesList.add(calveRaiseSmith)

            // --------------------------------------------FRONT DELTOIDS EXERCISES ---------------------------------------------
            val overHeadDumbbellPress =
                Exercise(30, "Dumbbell overhead press", 1, true, 120, FRONT_DELTS_POSITION,5,10,"A",false)
            allExercisesList.add(overHeadDumbbellPress)

            val overHeadPress =
                Exercise(31, "Overhead press", 1, true, 120,FRONT_DELTS_POSITION,5,10,"A",false)
            allExercisesList.add(overHeadPress)

            // --------------------------------------------SIDE DELTOIDS EXERCISES ---------------------------------------------
            val sideDumbbellRaises =
                Exercise(32, "Dumbbell side raises", 1, true, 60, SIDE_DELTS_POSITION,10,20,"A",false)
            allExercisesList.add(sideDumbbellRaises)



            val sideCableRaises =
                Exercise(34, "Cable side raises", 1, true, 60,SIDE_DELTS_POSITION,10,20,"B",false)
            allExercisesList.add(sideCableRaises)

            // --------------------------------------------REAR DELTOIDS EXERCISES ---------------------------------------------

            val facePulls =
                Exercise(33, "Face Pulls", 1, true, 90,REAR_DELTS_POSITION,10,20,"A",false)
            allExercisesList.add(facePulls)

            val rearDeltFlies =
                Exercise(36, "Rear Delt Flies", 1, true, 60, REAR_DELTS_POSITION,10,20,"A",false)
            allExercisesList.add(rearDeltFlies)

            val faceRow =
                Exercise(36, "Bench face row", 1, true, 90, REAR_DELTS_POSITION,10,20,"A",false)
            allExercisesList.add(faceRow)


            return allExercisesList
        }
    }

}