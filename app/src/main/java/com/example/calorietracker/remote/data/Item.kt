package com.example.calorietracker.remote.data

import com.google.gson.annotations.SerializedName

data class Item(
    val calories: Double,
    @SerializedName("carbohydrates_total_g") val carbohydratesTotalG: Double,
    @SerializedName("cholesterol_mg") val cholesterolMg: Int,
    @SerializedName("fat_saturated_g") val fatSaturatedG: Double,
    @SerializedName("fat_total_g") val fatTotalG: Double,
    @SerializedName("fiber_g") val fiberG: Int,
    val name: String,
    @SerializedName("potassium_mg") val potassiumMg: Int,
    @SerializedName("protein_g") val proteinG: Double,
    @SerializedName("serving_size_g") val servingSizeG: Int,
    @SerializedName("sodium_mg") val sodiumMg: Int,
    @SerializedName("sugar_g") val sugarG: Int
)