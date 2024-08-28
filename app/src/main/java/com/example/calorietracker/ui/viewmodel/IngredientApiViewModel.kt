package com.example.calorietracker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.remote.repo.IngredientApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientApiViewModel @Inject constructor(
    private val ingredientApiRepo: IngredientApiRepo
): ViewModel() {

    init {
        getIngredientNutritionalInfo("Milk")
    }

    fun getIngredientNutritionalInfo(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val ingredientInfo = ingredientApiRepo.getIngredientNutritionalInfo(query)
            Log.d("IngredientDataFromAPI", ingredientInfo.items.toString())
        }
    }
}