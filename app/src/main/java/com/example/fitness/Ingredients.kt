package com.example.fitness

data class Ingredients(
    var name: String,
    var kind: String,
    var calories: Double,
    var protein: Double,
    var carbs: Double,
    var fats: Double
){
    override fun toString(): String {
        return "$name\t$kind\t$calories\t$protein\t$carbs\t$fats"
    }

}