package com.example.calorietracker.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorietracker.ui.Screens
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel

@Composable
fun AppScreen(
    navController: NavHostController,
    databaseViewModel: DatabaseViewModel,
    onboardingViewModel: OnboardingViewModel
) {
    NavHost(navController = navController, startDestination = Screens.HOME_SCREEN.name) {
        composable(Screens.HOME_SCREEN.name) {
            HomeScreen(
                onAddButtonClicked = {
                    navController.navigate(Screens.MEAL_ADD_SCREEN.name)
                },
                databaseViewModel = databaseViewModel,
                onboardingViewModel = onboardingViewModel
            )
        }
        composable(Screens.MEAL_ADD_SCREEN.name) {
            MealAddingScreen(
                onCancelButtonClicked = {
                    navController.popBackStack()
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                },
                databaseViewModel = databaseViewModel,
                onAddButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}