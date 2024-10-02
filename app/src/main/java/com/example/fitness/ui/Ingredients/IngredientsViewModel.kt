package com.example.fitness.ui.Ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitness.Ingredients

class IngredientsViewModel() : ViewModel() {


    private val ingredientLiveData = MutableLiveData<MutableList<Ingredients>>()
    val livedata: LiveData<MutableList<Ingredients>> = ingredientLiveData

    fun addIngredient(ingredient: MutableList<Ingredients>) {
        ingredientLiveData.value = ingredient
    }
}