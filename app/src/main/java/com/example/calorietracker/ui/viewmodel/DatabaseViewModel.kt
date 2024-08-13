package com.example.calorietracker.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.database.Meal
import com.example.calorietracker.database.repo.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// view model for connecting database with ui
@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val mealRepo: MealRepo
): ViewModel() {

    init {
        Log.d("InsertMeal", "inserting meals")
        insertMeal(
            Meal(
                1,
                "meal1"
            )
        )
        insertMeal(
            Meal(
                2,
                "meal2"
            )
        )
        insertMeal(
            Meal(
                3,
                "meal3"
            )
        )
    }

    // ui state to store list of meals and showing it on UI
    private val _mealUiState: StateFlow<MealUiState> = mealRepo.getAllMeals().map {
        MealUiState(
            meals = it
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MealUiState()
    )

    val mealUiState = _mealUiState

    // function for inserting meals
    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.insertMeal(meal)
        }
    }
}

// data class for storing list of meals
data class MealUiState(
    val meals: List<Meal> = emptyList()
)