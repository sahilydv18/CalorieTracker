package com.example.calorietracker.remote.repo

import com.example.calorietracker.remote.data.IngredientNutritionalData

interface IngredientApiRepo {
    suspend fun getIngredientNutritionalInfo(query: String): IngredientNutritionalData
}