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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calorietracker.ui.HomeScreen
import com.example.calorietracker.ui.onboarding.OnboardingScreen
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.theme.CalorieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()
            val shouldShowOnboarding by onboardingViewModel.shouldShowOnboardingScreen.collectAsState()

            CalorieTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (shouldShowOnboarding) {
                        OnboardingScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        HomeScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
