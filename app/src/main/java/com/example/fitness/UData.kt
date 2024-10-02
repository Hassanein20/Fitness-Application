package com.example.fitness

import java.io.Serializable

data class UData(
    val username: String,
    var name: String,
    val age: Int,
    var weight: Double,
    var height: Double,
    var goal: String,
    var activitylevel: String,
    var gender: String,
    var CalVariable: Int,
) : Serializable{

    var caloriesPerDay: Float
    var FinalCalories: Float

    init {
        this.caloriesPerDay = CaloreNeeded()
        this.FinalCalories = caloriesPerDay + CalVariable
    }


    private fun CaloreNeeded(): Float {

        val BMR: Double = if (gender == "Male") {
            (9.99 * weight) + (6.25 * height) - (4.92 * age) + 5
        } else {
            (9.99 * weight) + (6.25 * height) - (4.92 * age) - 161
        }

        var TDEE: Float = 0.00F

        when (activitylevel) {
            "Sedentary" -> {
                TDEE = (1.2 * BMR).toFloat()
            }

            "Lightly active" -> {
                TDEE = (1.375 * BMR).toFloat()
            }

            "Moderately active" -> {
                TDEE = (1.55 * BMR).toFloat()
            }

            "Very active" -> {
                TDEE = (1.725 * BMR).toFloat()
            }

            "Extremely active" -> {
                TDEE = (1.9 * BMR).toFloat()
            }
        }
        return TDEE
    }

    override fun toString(): String {
        return "$username\t$name\t$age\t$weight\t$height\t$goal\t$activitylevel\t$gender\t$caloriesPerDay\t$CalVariable\t$FinalCalories"
    }
}