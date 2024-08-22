package com.example.calorietracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.ui.Screens
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.screens.editing.BiometricInfoEditScreen
import com.example.calorietracker.ui.screens.editing.NutritionalInfoEditScreen
import com.example.calorietracker.ui.screens.editing.PersonalInfoEditScreen
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel

@Composable
fun AppScreen(
    navController: NavHostController,
    databaseViewModel: DatabaseViewModel,
    onboardingViewModel: OnboardingViewModel
) {

    var mealToEdit by remember {
        mutableStateOf<Meal?>(null)
    }
    var ingredientsForMealToEdit by remember {
        mutableStateOf<List<Ingredient>?>(null)
    }

    NavHost(navController = navController, startDestination = Screens.HOME_SCREEN.name) {
        composable(Screens.HOME_SCREEN.name) {
            HomeScreen(
                onAddButtonClicked = {
                    navController.navigate(Screens.MEAL_ADD_SCREEN.name)
                },
                databaseViewModel = databaseViewModel,
                onboardingViewModel = onboardingViewModel,
                onEditButtonClicked = { editedMeal, editedIngredients ->
                    mealToEdit = editedMeal
                    ingredientsForMealToEdit = editedIngredients
                    navController.navigate(Screens.MEAL_ADD_SCREEN.name)
                },
                onScreenChanged = { screen ->
                    navController.navigate(screen.name) {
                        popUpTo(Screens.HOME_SCREEN.name) {
                            inclusive = true
                            saveState = false
                        }
                    }
                }
            )
        }
        composable(Screens.MEAL_ADD_SCREEN.name) {
            MealAddingScreen(
                onCancelButtonClicked = {
                    navController.popBackStack()
                    mealToEdit = null
                    ingredientsForMealToEdit = null
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                    mealToEdit = null
                    ingredientsForMealToEdit = null
                },
                databaseViewModel = databaseViewModel,
                onAddButtonClicked = {
                    navController.popBackStack()
                    mealToEdit = null
                    ingredientsForMealToEdit = null
                },
                meal = mealToEdit,
                onboardingViewModel = onboardingViewModel,
                ingredient = ingredientsForMealToEdit
            )
        }
        composable(Screens.SETTINGS_SCREEN.name) {
            SettingsScreen(
                onScreenChanged = { screen ->
                    navController.navigate(screen.name) {
                        popUpTo(Screens.SETTINGS_SCREEN.name) {
                            inclusive = true
                            saveState = false
                        }
                    }
                },
                onEditButtonClicked = { screen ->
                    navController.navigate(screen.name)
                }
            )
        }
        composable(Screens.EDIT_PERSONAL_INFO_SCREEN.name) {
            PersonalInfoEditScreen(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onCancelButtonClicked = {
                    navController.popBackStack()
                },
                onboardingViewModel = onboardingViewModel,
                onSaveButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screens.EDIT_BIOMETRIC_INFO_SCREEN.name) {
            BiometricInfoEditScreen(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onCancelButtonClicked = {
                    navController.popBackStack()
                },
                onboardingViewModel = onboardingViewModel,
                onSaveButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screens.EDIT_NUTRITIONAL_INFO_SCREEN.name) {
            NutritionalInfoEditScreen(
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                onCancelButtonClicked = {
                    navController.popBackStack()
                },
                onboardingViewModel = onboardingViewModel,
                onSaveButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}