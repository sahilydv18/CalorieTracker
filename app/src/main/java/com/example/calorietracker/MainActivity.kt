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
import com.example.calorietracker.ui.viewmodel.IngredientApiViewModel
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

            val ingredientApiViewModel: IngredientApiViewModel = hiltViewModel()

            val navController: NavHostController = rememberNavController()

            /*
                using this approach because it allows us to perform both the checks for onboarding screen value and for meals data is loaded.
                It checks for the meal data is loaded or not from the room database thus making sure that the list is successfully populated before
                removing the splash screen if there is meals data. And if there is no meals data it also make sure that the splash screen is removed as
                the value for the presence of data is also successfully loaded from the database. And also while it waits for the successfully loading
                of data, in that meantime the onboarding screen value (whether to show it or not) is also updated.
                Thus making this approach the best choice.
            */
            splashScreen.setKeepOnScreenCondition {
                !databaseViewModel.mealUiState.value.isMealsDataLoaded
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
                            databaseViewModel = databaseViewModel,
                            onboardingViewModel = onboardingViewModel
                        )
                    }
                }
            }
        }
    }
}
