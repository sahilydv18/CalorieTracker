package com.example.calorietracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val ingredientID: Int = 0,
    val name: String,
    val quantity: String,
    val calories: String,
    val protein: String,
    val fat: String,
    val carbs: String
)
