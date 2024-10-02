package com.example.fitness.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val calories = MutableLiveData<Float>()
    val livedata: LiveData<Float> = calories

    private val rcalories = MutableLiveData<Float>()
    val livedatar: LiveData<Float> = rcalories

    private val ingredientc = MutableLiveData<Float>()
    val livedatain: LiveData<Float> = ingredientc

    private val protein = MutableLiveData<Double>()
    val livedatap: LiveData<Double> = protein

    private val carbs = MutableLiveData<Double>()
    val livedatac: LiveData<Double> = carbs

    private val fats = MutableLiveData<Double>()
    val livedataf: LiveData<Double> = fats

    private val isStopped = MutableLiveData<Boolean>()
    val livedataS: LiveData<Boolean> = isStopped

    fun DailyCalories(calorie: Float) {
        calories.value = calorie
        rcalories.value = calories.value
    }
    fun rCalories(Remaining: Float){
        rcalories.value = Remaining
    }

    fun addMacros(calories: Float, p: Double, carb: Double, f: Double) {
        var calorie = ingredientc.value
        if (calorie == (null)) {
            calorie = (0.00).toFloat()
        }
        var car = carbs.value
        if (car == (null)) {
            car = (0.00).toDouble()
        }
        var prot = protein.value
        if (prot == (null)) {
            prot = (0.00).toDouble()
        }
        var fat = fats.value
        if (fat == (null)) {
            fat = (0.00).toDouble()
        }
        var cal = this.calories.value
        if(cal == null){
            cal = (0.00).toFloat()
        }

        val r = ((calories) + calorie)
        ingredientc.value = r
        protein.value = (p) + prot
        carbs.value = (carb) + car
        fats.value = (f) + fat
        rcalories.value = cal - r

    }


}