package com.example.calorietracker.remote

import com.example.calorietracker.remote.data.IngredientNutritionalData
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientApi {
    // function for getting ingredient nutritional data using api
    @GET("v1/nutrition")
    suspend fun getIngredientNutritionalInfo(
        @Query("query") query: String
    ): IngredientNutritionalData
}