package com.example.calorietracker.ui.viewmodel

import com.example.calorietracker.remote.data.IngredientNutritionalData

sealed interface IngredientApiUiState {
    data class Success(val ingredientNutritionalData: IngredientNutritionalData): IngredientApiUiState
    data object Loading: IngredientApiUiState
    data object Error: IngredientApiUiState
}