package com.example.calorietracker.remote.data

import com.google.gson.annotations.SerializedName

data class Item(
    val calories: Double,
    @SerializedName("carbohydrates_total_g") val carbohydratesTotalG: Double,
    @SerializedName("cholesterol_mg") val cholesterolMg: Double,
    @SerializedName("fat_saturated_g") val fatSaturatedG: Double,
    @SerializedName("fat_total_g") val fatTotalG: Double,
    @SerializedName("fiber_g") val fiberG: Double,
    val name: String,
    @SerializedName("potassium_mg") val potassiumMg: Double,
    @SerializedName("protein_g") val proteinG: Double,
    @SerializedName("serving_size_g") val servingSizeG: Double,
    @SerializedName("sodium_mg") val sodiumMg: Double,
    @SerializedName("sugar_g") val sugarG: Double
)