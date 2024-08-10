package com.example.calorietracker.calculation

import kotlin.math.roundToInt

object NutritionalCalculation {
    fun calculateCalories(pal: Double, weight: Double, height: Double, age: Double, male: Boolean): String {
        val requiredCalorie: Double = if (male) {
            ((weight * 10.0) + (height * 6.25) - (age * 5) + 5) * pal
        } else {
            ((weight * 10.0) + (height * 6.25) - (age * 5) - 161) * pal
        }
        return requiredCalorie.roundToInt().toString()
    }

    fun calculateProtein(weight: Double): String {
        val requiredProtein: Double = weight * 1.6
        return requiredProtein.roundToInt().toString()
    }

    fun calculateFat(calorie: Double): String {
        val requiredFat: Double = (((calorie / 100) * 35) / 9)
        return requiredFat.roundToInt().toString()
    }

    fun calculateCarbs(calorie: Double, protein: Double, fat: Double): String {
        val requiredCarbs: Double = (calorie - ((protein * 4) + (fat * 9))) / 4
        return requiredCarbs.roundToInt().toString()
    }
}