package com.example.calorietracker.ui.onboarding.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calorietracker.ui.Screens
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.screens.HomeScreen
import com.example.calorietracker.ui.screens.MealAddingScreen
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel

// implementing onboarding screen using navigation
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    databaseViewModel: DatabaseViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screens.FIRST_SCREEN.name
    ) {
        composable(Screens.FIRST_SCREEN.name) {
            FirstScreen(
                modifier = modifier,
                onNextButtonClicked = {
                    navController.navigate(Screens.SECOND_SCREEN.name)
                }
            )
        }
        composable(Screens.SECOND_SCREEN.name) {
            SecondScreen(
                modifier = modifier,
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
                modifier = modifier,
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
                onAddButtonClicked = {
                    navController.navigate(Screens.MEAL_ADD_SCREEN.name)
                },
                onboardingViewModel = onboardingViewModel,
                databaseViewModel = databaseViewModel
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