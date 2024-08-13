package com.example.calorietracker.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Meal::class,
        Ingredient::class,
        MealIngredients::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao
}