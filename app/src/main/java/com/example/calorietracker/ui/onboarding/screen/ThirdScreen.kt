package com.example.calorietracker.ui.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.calorietracker.R
import com.example.calorietracker.calculation.NutritionalCalculation
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// third screen for onboarding process
@Composable
fun ThirdScreen(
    onFinishButtonClicked: () -> Unit = {},
    onPreviousButtonClicked: () -> Unit = {},
    onboardingViewModel: OnboardingViewModel
) {

    // state variables for calculation of nutritional values
    val coroutineScope = rememberCoroutineScope()

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

    // updating value in the state variables for nutritional values calculation
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

    // state variable for lottie animation composition
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.third_screen))

    // state variable for calorie
    var calorie by rememberSaveable {
        mutableStateOf("")
    }

    // updating value of calorie state variable to use the calculated calorie value
    // using launched effect as we want only updated value to be shown in text field and not an empty string
    LaunchedEffect(age, pal, weight, height, gender, weightGoal) {
        if (age.isNotEmpty() && pal.isNotEmpty() && weight.isNotEmpty() &&
            height.isNotEmpty() && gender.isNotEmpty() && weightGoal.isNotEmpty()
        ) {
            val calculatedCalorie = NutritionalCalculation.calculateCalories(
                pal.toDoubleOrNull() ?: 0.0,
                weight.toDoubleOrNull() ?: 0.0,
                height.toDoubleOrNull() ?: 0.0,
                age.toDoubleOrNull() ?: 0.0,
                gender,
                weightGoal
            )
            calorie = calculatedCalorie
        }
    }

    // state variable for protein
    var protein by rememberSaveable {
        mutableStateOf("")
    }

    // updating value of protein state variable to use the calculated protein value
    // using launched effect as we want only updated value to be shown in text field and not an empty string
    LaunchedEffect(weight) {
        if (weight.isNotEmpty()) {
            val calculatedProtein = NutritionalCalculation.calculateProtein(weight.toDoubleOrNull() ?: 0.0)
            protein = calculatedProtein
        }
    }

    // state variable for fat
    var fat by rememberSaveable {
        mutableStateOf("")
    }

    // updating value of fat state variable to use the calculated fat value
    // using launched effect as we want only updated value to be shown in text field and not an empty string
    LaunchedEffect(calorie) {
        if (calorie.isNotEmpty()) {
            val calculatedFat = NutritionalCalculation.calculateFat(calorie.toDoubleOrNull() ?: 0.0)
            fat = calculatedFat
        }
    }

    // state variable for carbs
    var carbs by rememberSaveable {
        mutableStateOf("")
    }

    // updating value of carbs state variable to use the calculated carbs value
    // using launched effect as we want only updated value to be shown in text field and not an empty string
    LaunchedEffect(calorie, protein, fat) {
        if (calorie.isNotEmpty() && protein.isNotEmpty() && fat.isNotEmpty()) {
            val calculatedCarbs = NutritionalCalculation.calculateCarbs(
                calorie.toDoubleOrNull() ?: 0.0,
                protein.toDoubleOrNull() ?: 0.0,
                fat.toDoubleOrNull() ?: 0.0
            )
            carbs = calculatedCarbs
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // buttons for navigating the onboarding screens
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { onPreviousButtonClicked() }) {
                    Text(text = stringResource(id = R.string.previous))
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onFinishButtonClicked()
                        onboardingViewModel.updateShouldShowOnboardingScreen(false)
                        onboardingViewModel.updateCalorie(calorie.toInt())
                        onboardingViewModel.updateProtein(protein.toInt())
                        onboardingViewModel.updateCarbs(carbs.toInt())
                        onboardingViewModel.updateFat(fat.toInt())
                    },
                    enabled = calorie.isNotBlank() && protein.isNotBlank() && carbs.isNotBlank() && fat.isNotBlank()
                ) {
                    Text(text = stringResource(id = R.string.finish))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(360.dp)
            )
            Text(
                text = stringResource(id = R.string.third_screen_text),
                fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // calorie text field
                OutlinedTextField(
                    value = calorie,
                    onValueChange = { value ->
                        calorie = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.calorie))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                // protein text field
                OutlinedTextField(
                    value = protein,
                    onValueChange = { value ->
                        protein = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.protein))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                // carbs text field
                OutlinedTextField(
                    value = carbs,
                    onValueChange = { value ->
                        carbs = value.filter {      // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.carbs))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                // fat text field
                OutlinedTextField(
                    value = fat,
                    onValueChange = { value ->
                        fat = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.fat))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}