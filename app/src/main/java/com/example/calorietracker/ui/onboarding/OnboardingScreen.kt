package com.example.calorietracker.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calorietracker.ui.HomeScreen

// implementing onboarding screen using navigation
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

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
                    navController.navigate(Screens.FIRST_SCREEN.name)
                },
                onNextButtonClicked = {
                    navController.navigate(Screens.THIRD_SCREEN.name)
                }
            )
        }
        composable(Screens.THIRD_SCREEN.name) {
            ThirdScreen(
                onPreviousButtonClicked = {
                    navController.navigate(Screens.SECOND_SCREEN.name)
                },
                onFinishButtonClicked = {
                    navController.navigate(Screens.HOME_SCREEN.name)
                }
            )
        }
        composable(Screens.HOME_SCREEN.name) {
            HomeScreen(modifier)
        }
    }
}