package com.example.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calorietracker.ui.HomeScreen
import com.example.calorietracker.ui.HomeScreenFAB
import com.example.calorietracker.ui.HomeScreenTopAppBar
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.onboarding.screen.OnboardingScreen
import com.example.calorietracker.ui.theme.CalorieTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

            // NOTE : this is a temporary solution, fix it when you use database to store meals
            // checking if the list contains 1 element before closing the splash screen to make sure that the data is loaded for the data store preferences file
            splashScreen.setKeepOnScreenCondition {
                onboardingViewModel.conditionForSplashScreen.size < 1
            }

            var name by rememberSaveable {
                mutableStateOf("")
            }
            LaunchedEffect(Unit) {
                CoroutineScope(Dispatchers.IO).launch {
                    name = onboardingViewModel.getName()
                }
            }


            CalorieTrackerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        HomeScreenTopAppBar(name)
                    },
                    floatingActionButton = {
                        HomeScreenFAB()
                    }
                ) { innerPadding ->
                    if (shouldShowOnboarding) {
                        OnboardingScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}
