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

            val navController: NavHostController = rememberNavController()

            // NOTE : this is a temporary solution, fix it when you use database to store meals
            // checking if the list contains 1 element before closing the splash screen to make sure that the data is loaded for the data store preferences file
            splashScreen.setKeepOnScreenCondition {
                onboardingViewModel.conditionForSplashScreen.size < 1
            }

            CalorieTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (shouldShowOnboarding) {
                        OnboardingScreen(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController
                        )
                    } else {
                        AppScreen(
                            navController = navController,
                            innerPadding = innerPadding
                        )
                    }
                }
            }
        }
    }
}
