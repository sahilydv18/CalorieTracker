package com.example.calorietracker.remote.data

data class Item(
    val calories: Double,
    val carbohydrates_total_g: Double,
    val cholesterol_mg: Int,
    val fat_saturated_g: Double,
    val fat_total_g: Double,
    val fiber_g: Int,
    val name: String,
    val potassium_mg: Int,
    val protein_g: Double,
    val serving_size_g: Int,
    val sodium_mg: Int,
    val sugar_g: Int
)