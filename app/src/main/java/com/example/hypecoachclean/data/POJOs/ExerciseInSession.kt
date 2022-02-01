package com.example.hypecoachclean.data.POJOs

import com.example.hypecoachclean.Constants
import com.example.hypecoachclean.cloneSetsWithDifferentReference
import java.util.*
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
            sets.add(Set(tmpVolume + 1, 0, 0, 0))
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

    fun print(){
        this.exercise.print()
        print(volume.toString())
    }

    fun getExercise(): Exercise {
        return this.exercise
    }

    fun setExercise(theExercise : Exercise){
        this.exercise = theExercise
    }
    fun getVolume(): Int{
        return volume
    }
    fun getSets(): ArrayList<Set> {
        return sets
    }
    fun setSets(givenSets: ArrayList<Set>) {
        sets = givenSets
    }

    fun getProposedSets(): ArrayList<Set> {

    val theSets = cloneSetsWithDifferentReference(sets)

        for(set in sets) {
            if (set.reps < this.exercise!!.getMax() - 2  && set.reps > this.exercise!!.getMin()) {
                set.reps = (set.reps*1.2).toInt()
            }else if(set.reps < this.exercise!!.getMin() + 1){

                set.load = (set.load*1.2).toInt()
                set.reps = (set.reps*1.2).toInt()
            }else {
                if(set.load > 90) {
                    set.load = (set.load*1.1).toInt()
                }else{
                    set.load = (set.load*1.15).toInt()
                }
                set.reps = (set.reps*0.9).toInt()

            }
            if (set.rir !=0){
                set.rir--
            }
        }

        return theSets
    }

    fun setVolume(theSets : Int){
        this.volume = theSets
    }

}