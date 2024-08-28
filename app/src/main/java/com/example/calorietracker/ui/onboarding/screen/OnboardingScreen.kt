package com.example.calorietracker.ui.onboarding.screen

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
import com.example.calorietracker.ui.screens.HomeScreen
import com.example.calorietracker.ui.screens.MealAddingScreen
import com.example.calorietracker.ui.screens.SettingsScreen
import com.example.calorietracker.ui.screens.editing.BiometricInfoEditScreen
import com.example.calorietracker.ui.screens.editing.NutritionalInfoEditScreen
import com.example.calorietracker.ui.screens.editing.PersonalInfoEditScreen
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel
import com.example.calorietracker.ui.viewmodel.IngredientApiViewModel

// implementing onboarding screen using navigation
@Composable
fun OnboardingScreen(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    databaseViewModel: DatabaseViewModel,
    ingredientApiViewModel: IngredientApiViewModel
) {

    var mealToEdit by remember {
        mutableStateOf<Meal?>(null)
    }
    var ingredientsForMealToEdit by remember {
        mutableStateOf<List<Ingredient>?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = Screens.FIRST_SCREEN.name
    ) {
        composable(Screens.FIRST_SCREEN.name) {
            FirstScreen(
                onNextButtonClicked = {
                    navController.navigate(Screens.SECOND_SCREEN.name)
                },
                onboardingViewModel = onboardingViewModel
            )
        }
        composable(Screens.SECOND_SCREEN.name) {
            SecondScreen(
                onPreviousButtonClicked = {
                    navController.popBackStack()
                },
                onNextButtonClicked = {
                    navController.navigate(Screens.THIRD_SCREEN.name)
                },
                onboardingViewModel = onboardingViewModel
            )
        }
        composable(Screens.THIRD_SCREEN.name) {
            ThirdScreen(
                onPreviousButtonClicked = {
                    navController.popBackStack()
                },
                onFinishButtonClicked = {
                    navController.popBackStack(route = Screens.FIRST_SCREEN.name, inclusive = true)
                    navController.navigate(Screens.HOME_SCREEN.name)
                },
                onboardingViewModel = onboardingViewModel
            )
        }
        composable(Screens.HOME_SCREEN.name) {
            HomeScreen(
                onAddButtonClicked = {
                    navController.navigate(Screens.MEAL_ADD_SCREEN.name)
                },
                onboardingViewModel = onboardingViewModel,
                databaseViewModel = databaseViewModel,
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
                ingredient = ingredientsForMealToEdit,
                onboardingViewModel = onboardingViewModel,
                ingredientApiViewModel = ingredientApiViewModel
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