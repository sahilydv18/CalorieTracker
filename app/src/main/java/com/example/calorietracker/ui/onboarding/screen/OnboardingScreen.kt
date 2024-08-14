package com.example.calorietracker.ui.onboarding.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorietracker.ui.onboarding.Screens
import com.example.calorietracker.ui.screens.HomeScreen
import com.example.calorietracker.ui.screens.MealAddingScreen

// implementing onboarding screen using navigation
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.FIRST_SCREEN.name,
        modifier = modifier
    ) {
        composable(Screens.FIRST_SCREEN.name) {
            FirstScreen(
                onNextButtonClicked = {
                    navController.navigate(Screens.SECOND_SCREEN.name)
                }
            )
        }
        composable(Screens.SECOND_SCREEN.name) {
            SecondScreen(
                onPreviousButtonClicked = {
                    navController.popBackStack()
                    //navController.navigate(Screens.FIRST_SCREEN.name)
                },
                onNextButtonClicked = {
                    navController.navigate(Screens.THIRD_SCREEN.name)
                }
            )
        }
        composable(Screens.THIRD_SCREEN.name) {
            ThirdScreen(
                onPreviousButtonClicked = {
                    navController.popBackStack()
                    //navController.navigate(Screens.SECOND_SCREEN.name)
                },
                onFinishButtonClicked = {
                    navController.popBackStack(route = Screens.FIRST_SCREEN.name, inclusive = true)
                    navController.navigate(Screens.HOME_SCREEN.name)
                }
            )
        }
        composable(Screens.HOME_SCREEN.name) {
            HomeScreen(
                modifier,
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