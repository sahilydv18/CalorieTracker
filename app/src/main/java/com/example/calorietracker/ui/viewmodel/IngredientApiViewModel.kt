package com.example.calorietracker.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.remote.repo.IngredientApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

// ViewModel for connecting with api via repo
@HiltViewModel
class IngredientApiViewModel @Inject constructor(
    private val ingredientApiRepo: IngredientApiRepo
): ViewModel() {
    // ui state for showing information on screen
    var ingredientApiUiState: IngredientApiUiState by mutableStateOf(IngredientApiUiState.Loading)

    fun getIngredientNutritionalInfo(ingredientName: String, ingredientQuantity: String) {
        val query = "$ingredientQuantity $ingredientName"
        viewModelScope.launch(Dispatchers.IO) {
            ingredientApiUiState = try {
                val ingredientNutritionalInfo = ingredientApiRepo.getIngredientNutritionalInfo(query)
                IngredientApiUiState.Success(ingredientNutritionalInfo)
            } catch (e: IOException) {
                IngredientApiUiState.Error
            }
        }
    }
}