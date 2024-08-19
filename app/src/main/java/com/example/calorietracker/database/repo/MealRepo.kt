package com.example.calorietracker.database.repo

import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.database.MealIngredients
import kotlinx.coroutines.flow.Flow

interface MealRepo {
    fun getIngredientsForMeal(mealId: Int): Flow<List<Ingredient>>

    fun getAllMeals(): Flow<List<Meal>>

    suspend fun getIngredient(ingredientID: Int): Ingredient?

    suspend fun insertMeal(meal: Meal): Long

    suspend fun insertIngredient(ingredient: Ingredient): Long

    suspend fun insertIngredientsForMeal(mealIngredients: MealIngredients)

    suspend fun deleteMeal(meal: Meal)

    suspend fun deleteIngredient(ingredient: Ingredient)

    suspend fun deleteIngredientsForMeal(mealId: Int)

    suspend fun updateMealCompletedStatus(mealId: Int, isCompleted: Boolean)

    suspend fun updateMealAndIngredients(updatedMeal: Meal, updatedIngredients: List<Ingredient>, oldIngredients: List<Ingredient>)
}