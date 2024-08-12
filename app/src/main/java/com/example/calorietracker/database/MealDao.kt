package com.example.calorietracker.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// dao for accessing the database
@Dao
interface MealDao {

    @Query(
        """
        SELECT * FROM Ingredient 
        INNER JOIN MealIngredients ON Ingredient.ingredientId = MealIngredients.ingredientId
        WHERE MealIngredients.mealId = :mealId
        """
    )
    fun getIngredientsForMeal(mealId: Int): Flow<List<Ingredient>>
}