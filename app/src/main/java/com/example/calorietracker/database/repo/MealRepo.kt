package com.example.calorietracker.database.repo

import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.database.MealIngredients
import kotlinx.coroutines.flow.Flow

interface MealRepo {
    fun getIngredientsForMeal(mealId: Int): Flow<List<Ingredient>>

    fun getAllMeals(): Flow<List<Meal>>

    suspend fun getIngredient(ingredientID: Int): Ingredient?

    suspend fun insertMeal(meal: Meal)

    suspend fun insertIngredient(ingredient: Ingredient)

    suspend fun insertIngredientsForMeal(mealIngredients: MealIngredients)

    suspend fun deleteMeal(meal: Meal)

    suspend fun deleteIngredient(ingredient: Ingredient)

    suspend fun deleteIngredientsForMeal(mealId: Int)
}