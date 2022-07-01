package com.example.hypecoachclean.data.BusinessLogic

import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.cloneSetsWithDifferentReference
import kotlin.collections.ArrayList


data class ExerciseInSession(
    private var primarity: Boolean,
    private var volume: Int,
    private var movementType: String,
    private var muscleGroupCode: Int,
    private var exerciseSource: ArrayList<Exercise>

){
    private var exercise = Exercise(5, "-----------------", 1, true, 120,
        Constants.CHEST_POSITION,5,12,"A",true)
    private var sets = ArrayList<Set>()
    var isTheFirstRun = true

    init{
        var tmpVolume = 0

        while(tmpVolume!= volume){
            sets.add(Set(tmpVolume + 1, 0.0, 0, 0))
            tmpVolume++
        }
        exerciseSource.shuffle()
        for((j, i) in exerciseSource.withIndex()) {
            if(!i.isIncluded()  //Finding an exercise that fits
                && this.muscleGroupCode == i.getFocus()
                && this.primarity == i.getPrimarity()
                && (this.movementType == i.getMovementType() || this.movementType == "All")){
                i.setIncluded(true)
                this.exercise = i
                break;
            }
        }
    }

    fun getSetSize(): Int {
        return sets.size
    }
    fun addSet(set: Set){
        sets.add(set)
    }

    fun getProposedSets(): ArrayList<Set> {

    val theSets = cloneSetsWithDifferentReference(sets)

        for(set in sets) {
            if (set.reps < this.exercise.getMax() - 1  && set.reps > this.exercise.getMin()-1) {
                if(set.load > 20) { set.reps = (set.reps * 1.2).toInt()
                    }else if(set.load > 60){ set.reps = (set.reps * 1.1).toInt()
                    }else if(set.load > 80){ set.reps = (set.reps * 1.08).toInt()
                    }else if(set.load > 100){ set.reps = (set.reps * 1.05).toInt()
                }
            }else if(set.reps < this.exercise.getMin() + 1){
                set.load = set.load*1.1
                set.reps = (set.reps*1.2).toInt()
            }else {
                when {
                    set.load>10 -> {
                        set.load = set.load * 1.2
                    }
                    set.load>20 -> {
                        set.load = set.load * 1.15
                    }
                    set.load>30 -> {
                        set.load = set.load * 1.1
                    }
                    set.load>40 -> {
                        set.load = set.load * 1.05
                    }
                    set.load>60 -> {
                        set.load = set.load * 1.05
                    }
                    set.load>70 -> {
                        set.load = set.load * 1.02
                    }
                    set.load>80 -> {
                        set.load = set.load * 1.02
                    }
                    set.load>90 -> {
                        set.load = set.load * 1.01
                    }
                }

                set.reps = (set.reps*0.9).toInt()
            }
            if (set.rir !=0)    set.rir--
        }
        return theSets
    }

    fun getExercise(): Exercise {
        return this.exercise
    }

    fun getSets(): ArrayList<Set> {
        return sets
    }
    fun setSets(givenSets: ArrayList<Set>) {
        sets = givenSets
    }


    fun print(){
        this.exercise.print()
        print(volume.toString())
    }
}