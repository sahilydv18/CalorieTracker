package com.example.calorietracker.ui.screens.editing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.calorietracker.R
import com.example.calorietracker.ui.EditingScreenBottomAppBar
import com.example.calorietracker.ui.EditingScreenTopAppBar
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NutritionalInfoEditScreen(
    onBackButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onboardingViewModel: OnboardingViewModel,
    onSaveButtonClicked: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    // state variable for calorie
    var calorie by remember {
        mutableStateOf("")
    }

    // state variable for protein
    var protein by remember {
        mutableStateOf("")
    }

    // state variable for carbs
    var carbs by remember {
        mutableStateOf("")
    }

    // state variable for fat
    var fat by remember {
        mutableStateOf("")
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

    Scaffold(
        topBar = {
            EditingScreenTopAppBar(title = R.string.edit_nutritional_info) {
                onBackButtonClicked()
            }
        },
        bottomBar = {
            EditingScreenBottomAppBar(
                onCancelButtonClicked = { onCancelButtonClicked() },
                onSaveButtonClicked = {

                    onboardingViewModel.updateCalorie(calorie.toInt())
                    onboardingViewModel.updateProtein(protein.toInt())
                    onboardingViewModel.updateCarbs(carbs.toInt())
                    onboardingViewModel.updateFat(fat.toInt())

                    onSaveButtonClicked()
                },
                saveButtonEnabled = calorie.isNotBlank() && protein.isNotBlank() && carbs.isNotBlank() && fat.isNotBlank()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.nutritional_info),
                contentDescription = stringResource(id = R.string.edit_nutritional_info),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .size(300.dp)
            )

            // calorie text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.calorie_goal),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = calorie,
                    onValueChange = { value ->
                        calorie = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                )
            }

            // protein text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.protein_goal),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = protein,
                    onValueChange = { value ->
                        protein = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                )
            }

            // carbs text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.carbs_goal),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = carbs,
                    onValueChange = { value ->
                        carbs = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                )
            }

            // fat text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.fat_goal),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = fat,
                    onValueChange = { value ->
                        fat = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                )
            }
        }
    }
}