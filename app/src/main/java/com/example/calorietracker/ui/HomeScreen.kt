package com.example.calorietracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    var name by rememberSaveable {
        mutableStateOf("")
    }

    var age by rememberSaveable {
        mutableStateOf("")
    }

    var gender by rememberSaveable {
        mutableStateOf("")
    }

    var weight by rememberSaveable {
        mutableStateOf("")
    }
    var height by rememberSaveable {
        mutableStateOf("")
    }
    var pal by rememberSaveable {
        mutableStateOf("")
    }
    var weightGoal by rememberSaveable {
        mutableStateOf("")
    }
    var calorie by rememberSaveable {
        mutableStateOf("")
    }
    var protein by rememberSaveable {
        mutableStateOf("")
    }
    var carbs by rememberSaveable {
        mutableStateOf("")
    }
    var fat by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                name = onboardingViewModel.getName()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                age = onboardingViewModel.getAge()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                gender = onboardingViewModel.getGender()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                weight = onboardingViewModel.getWeight()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                height = onboardingViewModel.getHeight()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                pal = onboardingViewModel.getPAL()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                weightGoal = onboardingViewModel.getWeightGoal()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                calorie = onboardingViewModel.getCalorie()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                protein = onboardingViewModel.getProtein()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                carbs = onboardingViewModel.getCarbs()
            }
        }
        LaunchedEffect(Unit) {
            coroutineScope.launch(Dispatchers.IO) {
                fat = onboardingViewModel.getFat()
            }
        }

        Text(text = "Welcome $name to home screen")
        Text(text = "Age = $age")
        Text(text = "Gender = $gender")
        Text(text = "Weight = $weight")
        Text(text = "Height = $height")
        Text(text = "PAL = $pal")
        Text(text = "Weight Goal = $weightGoal")
        Text(text = "Calorie = $calorie")
        Text(text = "Protein = $protein")
        Text(text = "Carbs = $carbs")
        Text(text = "Fat = $fat")
    }
}