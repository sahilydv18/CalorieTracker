package com.example.calorietracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) val mealID: Int = 0,
    val mealName: String
)
