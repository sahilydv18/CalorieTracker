package com.example.calorietracker.database.repo

import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.database.MealDao
import com.example.calorietracker.database.MealIngredients
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepoImpl @Inject constructor(
    private val mealDao: MealDao
): MealRepo {
    override fun getIngredientsForMeal(mealId: Int): Flow<List<Ingredient>> {
        return mealDao.getIngredientsForMeal(mealId)
    }

    override fun getAllMeals(): Flow<List<Meal>> {
        return mealDao.getAllMeals()
    }

    override suspend fun getIngredient(ingredientID: Int): Ingredient? {
        return mealDao.getIngredient(ingredientID)
    }

    override suspend fun insertMeal(meal: Meal): Long {
        return mealDao.insertMeal(meal)
    }

    override suspend fun insertIngredient(ingredient: Ingredient): Long {
        return mealDao.insertIngredient(ingredient)
    }

    override suspend fun insertIngredientsForMeal(mealIngredients: MealIngredients) {
        mealDao.insertIngredientsForMeal(mealIngredients)
    }

    override suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }

    override suspend fun deleteIngredient(ingredient: Ingredient) {
        mealDao.deleteIngredient(ingredient)
    }

    override suspend fun deleteIngredientsForMeal(mealId: Int) {
        mealDao.deleteIngredientsForMeal(mealId)
    }
}