package com.example.calorietracker.remote.repo

import com.example.calorietracker.remote.IngredientApi
import com.example.calorietracker.remote.data.IngredientNutritionalData
import javax.inject.Inject

class IngredientApiRepoImpl @Inject constructor(
    private val ingredientApi: IngredientApi
): IngredientApiRepo {
    override suspend fun getIngredientNutritionalInfo(query: String): IngredientNutritionalData {
        return ingredientApi.getIngredientNutritionalInfo(query)
    }
}