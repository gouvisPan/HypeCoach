package com.example.hypecoachclean.data.BusinessLogic

//TODO Change privates
data class Exercise (
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var primarity: Boolean,
    private var restTime: Int,
    private var focus: Int,
    private var minReps: Int,
    private var maxReps: Int,
    private var movementType: String,
    private var included: Boolean
){

    fun print(){
        print("$name  ")
    }

    fun isIncluded():Boolean{
        return  included
    }

    fun setIncluded(action: Boolean){
        included=action
    }

    fun getMovementType():String{
        return movementType
    }

    fun getPrimarity(): Boolean{
        return primarity
    }

    fun getId(): Int {
        return id
    }

    fun getMax():Int{
        return maxReps
    }

    fun getMin():Int{
        return minReps
    }
    fun getRange(): String{
        return "$minReps-$maxReps"
    }

    fun getName(): String {
        return name
    }


    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getRest(): Int {
        return restTime
    }



    fun getFocus(): Int {
        return focus
    }


}