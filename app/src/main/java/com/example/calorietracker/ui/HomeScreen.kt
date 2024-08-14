package com.example.calorietracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    databaseViewModel: DatabaseViewModel = hiltViewModel()
) {

    // val mealUiState = databaseViewModel.mealUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var name by rememberSaveable {
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

    var completedCalorie by rememberSaveable {
        mutableFloatStateOf(2500F)
    }
    var completedProtein by rememberSaveable {
        mutableFloatStateOf(80F)
    }
    var completedCarbs by rememberSaveable {
        mutableFloatStateOf(120F)
    }
    var completedFat by rememberSaveable {
        mutableFloatStateOf(100F)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeScreenTopAppBar(name = name)
        },
        floatingActionButton = {
            HomeScreenFAB()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LaunchedEffect(Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    name = onboardingViewModel.getName()
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

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier.align(Alignment.CenterVertically)
                    ) {
                        NutritionalProgressIndicators(
                            completedValue = completedCalorie,
                            totalValue = calorie,
                            color = Color(red = 3, green = 252, blue = 40),
                            modifier = Modifier
                                .size(180.dp)
                                .align(Alignment.Center)
                        )
                        NutritionalProgressIndicators(
                            completedValue = completedProtein,
                            totalValue = protein,
                            color = Color(254, 177, 24),
                            modifier = Modifier
                                .size(150.dp)
                                .align(Alignment.Center)
                        )
                        NutritionalProgressIndicators(
                            completedValue = completedCarbs,
                            totalValue = carbs,
                            color = Color(241, 255, 24),
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.Center)
                        )
                        NutritionalProgressIndicators(
                            completedValue = completedFat,
                            totalValue = fat,
                            color = Color(252, 84, 75),
                            modifier = Modifier
                                .size(90.dp)
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NutritionalInfo(
                            title = "Calorie",
                            completedValue = completedCalorie,
                            totalValue = calorie,
                            unit = "kcal",
                            Color(red = 3, green = 252, blue = 40)
                        )
                        NutritionalInfo(
                            title = "Protein",
                            completedValue = completedProtein,
                            totalValue = protein,
                            unit = "g",
                            Color(254, 177, 24)
                        )
                        NutritionalInfo(
                            title = "Carbs",
                            completedValue = completedCarbs,
                            totalValue = carbs,
                            unit = "g",
                            Color(241, 255, 24)
                        )
                        NutritionalInfo(
                            title = "Fat",
                            completedValue = completedFat,
                            totalValue = fat,
                            unit = "g",
                            Color(252, 84, 75)
                        )
                    }
                }


//        Text(text = "Welcome $name to home screen")
//        Text(text = "List of meal: ")
//        LazyColumn {
//            items(mealUiState.value.meals) {
//                Text(text = it.mealName)
//            }
//        }
            }
        }
    }

}

@Composable
private fun NutritionalProgressIndicators(
    completedValue: Float,
    totalValue: String,
    color: Color,
    modifier: Modifier
) {
    CircularProgressIndicator(
        progress = {
            if (totalValue.isNullOrEmpty()) {
                (completedValue.toFloat() / 1F)
            } else {
                (completedValue.toFloat() / totalValue.toFloat())
            }
        },
        color = color,
        trackColor = Color.White,
        modifier = modifier
    )
}

@Composable
private fun NutritionalInfo(
    title: String,
    completedValue: Float,
    totalValue: String,
    unit: String,
    color: Color
) {
    Column(
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${String.format("%.0f", completedValue)}/$totalValue $unit",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = color
        )
    }
}