package com.example.calorietracker.database

import androidx.room.Entity
import androidx.room.ForeignKey

// connecting the meal table and ingredient table to get ingredients for a specific meal
@Entity(
    primaryKeys = ["mealID", "ingredientID"],
    foreignKeys = [
        ForeignKey(
            entity = Meal::class,
            parentColumns = ["mealID"],
            childColumns = ["mealID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["ingredientID"],
            childColumns = ["ingredientID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MealIngredients(
    val mealID: Int,
    val ingredientID: Int,
)
