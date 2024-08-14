package com.example.calorietracker.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorietracker.ui.onboarding.Screens

@Composable
fun AppScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = Screens.HOME_SCREEN.name) {
        composable(Screens.HOME_SCREEN.name) {
            HomeScreen(
                modifier = Modifier.padding(innerPadding),
                onAddButtonClicked = {
                    navController.navigate(Screens.MEAL_ADD_SCREEN.name)
                }
            )
        }
        composable(Screens.MEAL_ADD_SCREEN.name) {
            MealAddingScreen(
                onCancelButtonClicked = {
                    navController.popBackStack()
                },
                onBackButtonClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}