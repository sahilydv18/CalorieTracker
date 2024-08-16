package com.example.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.onboarding.screen.OnboardingScreen
import com.example.calorietracker.ui.screens.AppScreen
import com.example.calorietracker.ui.theme.CalorieTrackerTheme
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // variable for using new splash screen api
        val splashScreen = installSplashScreen()

        setContent {
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()
            val shouldShowOnboarding by onboardingViewModel.shouldShowOnboardingScreen.collectAsState()

            val databaseViewModel: DatabaseViewModel = hiltViewModel()

            val navController: NavHostController = rememberNavController()

            // checking if the meal list is populated before removing splash screen
            splashScreen.setKeepOnScreenCondition {
                databaseViewModel.mealUiState.value.meals.isEmpty()
            }

            CalorieTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (shouldShowOnboarding) {
                        OnboardingScreen(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            onboardingViewModel = onboardingViewModel,
                            databaseViewModel = databaseViewModel
                        )
                    } else {
                        AppScreen(
                            navController = navController,
                            innerPadding = innerPadding,
                            databaseViewModel = databaseViewModel,
                            onboardingViewModel = onboardingViewModel
                        )
                    }
                }
            }
        }
    }
}
