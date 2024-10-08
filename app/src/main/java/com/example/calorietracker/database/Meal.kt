package com.example.calorietracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// table for storing meal
@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true) val mealID: Int = 0,
    val mealName: String,
    val totalCalorie: String,
    val totalProtein: String,
    val totalCarbs: String,
    val totalFat: String,
    val isMealCompleted: Boolean = false
)
