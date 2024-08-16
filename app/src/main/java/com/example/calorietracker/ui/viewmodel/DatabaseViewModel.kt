package com.example.calorietracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.database.MealIngredients
import com.example.calorietracker.database.repo.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
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

    // function for getting ingredients for a meal
    fun getIngredientsForMeal(mealId: Int): Flow<List<Ingredient>> {
        return mealRepo.getIngredientsForMeal(mealId)
    }

    // function for inserting meals
    private suspend fun insertMeal(meal: Meal): Long {
        return mealRepo.insertMeal(meal)
    }

    // function for inserting ingredients
    private suspend fun insertIngredient(ingredientItem: IngredientItem): Long {
        return mealRepo.insertIngredient(ingredientItem.toIngredient())
    }

    fun updateMealCompletedStatus(mealId: Int, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.updateMealCompletedStatus(mealId, isCompleted)
        }
    }

    fun insertIngredientsForMeal(meal: Meal, mealIngredients: List<IngredientItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            coroutineScope {
                // Insert Meal and get ID
                val mealId = insertMeal(meal)

                // Insert Ingredients and getIDs (sequentially)
                val ingredientIds = mealIngredients.map { ingredientItem ->
                    insertIngredient(ingredientItem)
                }

                // Insert MealIngredients
                ingredientIds.forEach { ingredientId ->
                    mealRepo.insertIngredientsForMeal(
                        MealIngredients(
                            mealID = mealId.toInt(),
                            ingredientID = ingredientId.toInt()
                        )
                    )
                }
            }
        }
    }
}

// data class for storing list of meals
data class MealUiState(
    val meals: List<Meal> = emptyList()
)

// data class for adding ingredients, created so that we can use the auto generate functionality of primary key
data class IngredientItem(
    val ingredientID: Int = 0,
    val name: String,
    val quantity: String,
    val calories: String,
    val protein: String,
    val fat: String,
    val carbs: String
)

// extension function for converting IngredientItem to Ingredient
fun IngredientItem.toIngredient() = Ingredient(
    ingredientID = ingredientID,
    name = name,
    quantity = quantity,
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs
)

// extension function for converting Ingredient to IngredientItem
fun Ingredient.toIngredientItem() = IngredientItem(
    ingredientID = ingredientID,
    name = name,
    quantity = quantity,
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs
)