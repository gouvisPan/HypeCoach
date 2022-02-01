package com.example.hypecoachclean.data.POJOs


class Session(var volumeList: ArrayList<Int>,var availableExercises: ArrayList<Exercise>,var name: String,var id: Long) {

    var exercises = ArrayList<ExerciseInSession>()
    private var Done = false

    init {
        var exerciseTmp1: ExerciseInSession
        var exerciseTmp2: ExerciseInSession

        for ((position, volume) in volumeList.withIndex()) {
            if (volume != 0) { //only for the exercises that focus on the desired MuscleGroups
                println(volume)
                if(volume < 6) {//if the sets are 5 or less, create one exercise, else split the total volume into two
                    exerciseTmp1 =
                        ExerciseInSession(true, volume, "All", position, availableExercises)
                    exercises.add(exerciseTmp1)
                } else {
                    exerciseTmp1 =
                        ExerciseInSession(true, volume / 2 + 1, "A", position, availableExercises)
                    exerciseTmp2 =
                        ExerciseInSession(false, volume / 2 - 1, "B", position, availableExercises)
                    exercises.add(exerciseTmp1)
                    exercises.add(exerciseTmp2)
                }

            }
        }
    }
    fun clearAvailables(){
        availableExercises.clear()
    }
    fun increaseVolume(volumeValue: Int,muscle: Int) {
        var counter = volumeValue
        for (exercise in exercises) {//Increase the volume STARTING from the exercise with 2 sets (if there is any).
            //Then continue adding sets to the primary exercise

            if (exercise.getExercise().getFocus() == muscle && exercise.getSets().size < 3) {
                exercise.addSet(Set(exercise.getSetSize() + 1, 0, 0, 3))
                counter--
            }
        }
        if (counter > 0) {
            for (exercise in exercises) {
                if (counter>0 && exercise.getExercise().getFocus() == muscle) {
                    exercise.addSet(Set(exercise.getSetSize() + 1, 0, 0, 3))
                    counter--
                }
            }
        }
    }
    fun initialiseExerciseRuns(){
        for(i in exercises){
            i.isTheFirstRun = true
        }
    }
    fun isDone(): Boolean{
        return  Done
    }

    fun setNext(b: Boolean){
        Done = b
    }

    fun print() {

        for ((j, i) in exercises.withIndex()) {
            print("Exercise $j: ")
            i.print()
            println("")
        }
    }
}
