package com.example.calorietracker.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// dao for accessing the database
@Dao
interface MealDao {

    // function for getting all the ingredients for a meal
    @Query(
        """
        SELECT * FROM Ingredient 
        INNER JOIN MealIngredients ON Ingredient.ingredientId = MealIngredients.ingredientId
        WHERE MealIngredients.mealId = :mealId
        """
    )
    fun getIngredientsForMeal(mealId: Int): Flow<List<Ingredient>>

    // function for getting list of meal
    @Query("SELECT * FROM Meal")
    fun getAllMeals(): Flow<List<Meal>>

    // function for getting a ingredient
    @Query("SELECT * FROM Ingredient WHERE ingredientID = :ingredientID")
    suspend fun getIngredient(ingredientID: Int): Ingredient?

    // function for inserting meal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal): Long

    // function for inserting ingredients
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient): Long

    // function for inserting ingredients for meal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredientsForMeal(mealIngredients: MealIngredients)

    // function for deleting meal
    @Delete
    suspend fun deleteMeal(meal: Meal)

    // function for deleting ingredients
    @Delete
    suspend fun deleteIngredient(ingredient: Ingredient)

    // function for deleting all ingredients associated with a meal
    @Query("DELETE FROM MealIngredients WHERE mealId = :mealId")
    suspend fun deleteIngredientsForMeal(mealId: Int)

    // function for updating isMealCompleted
    @Query("UPDATE Meal SET isMealCompleted = :isCompleted WHERE mealID = :mealId")
    suspend fun updateMealCompletedStatus(mealId: Int, isCompleted: Boolean)
}