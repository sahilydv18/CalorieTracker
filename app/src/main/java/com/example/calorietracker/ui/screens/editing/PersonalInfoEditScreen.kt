package com.example.calorietracker.ui.screens.editing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.calorietracker.calculation.NutritionalCalculation
import com.example.calorietracker.ui.EditingScreenBottomAppBar
import com.example.calorietracker.ui.EditingScreenTopAppBar
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PersonalInfoEditScreen(
    onBackButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onboardingViewModel: OnboardingViewModel,
    onSaveButtonClicked: () -> Unit
) {

    // state variable for name
    var name by remember {
        mutableStateOf("")
    }

    // state variable for age
    var age by remember {
        mutableStateOf("")
    }

    // state variable for gender
    var selectedGender by rememberSaveable {
        mutableStateOf("Male")
    }

    val coroutineScope = rememberCoroutineScope()

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
            selectedGender = onboardingViewModel.getGender()
        }
    }

    // variable for getting value that are related to updating of nutritional goals
    val pal by produceState("") {
        value = onboardingViewModel.getPAL()
    }

    val height by produceState("") {
        value = onboardingViewModel.getHeight()
    }

    val weight by produceState("") {
        value = onboardingViewModel.getWeight()
    }

    val weightGoal by produceState("") {
        value = onboardingViewModel.getWeightGoal()
    }

    Scaffold(
        topBar = {
            EditingScreenTopAppBar(title = R.string.edit_personal_info) {
                onBackButtonClicked()
            }
        },
        bottomBar = {
            EditingScreenBottomAppBar(
                onCancelButtonClicked = {
                    onCancelButtonClicked()
                },
                onSaveButtonClicked = {
                    onboardingViewModel.updateName(name)
                    onboardingViewModel.updateAge(age.toInt())
                    onboardingViewModel.updateGender(selectedGender)

                    val calories = NutritionalCalculation.calculateCalories(
                        pal = pal.toDouble(),
                        age = age.toDouble(),
                        weight = weight.toDouble(),
                        height = height.toDouble(),
                        gender = selectedGender,
                        weightGoal = weightGoal
                    )

                    val protein = NutritionalCalculation.calculateProtein(weight.toDouble())

                    onboardingViewModel.updateCalorie(calories.toInt())

                    val fat = NutritionalCalculation.calculateFat(calories.toDouble())

                    onboardingViewModel.updateFat(fat.toInt())

                    onboardingViewModel.updateCarbs(NutritionalCalculation.calculateCarbs(
                        calorie = calories.toDouble(),
                        protein = protein.toDouble(),
                        fat = fat.toDouble()).toInt()
                    )

                    onSaveButtonClicked()

                },
                saveButtonEnabled = name.isNotBlank() && age.isNotBlank()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.personal_info),
                contentDescription = stringResource(id = R.string.edit_personal_info),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .size(300.dp)
            )
            // name text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.name),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
            }

            // age text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.age),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = age,
                    onValueChange = { value ->
                        age = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                            it.isDigit()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                )
            }

            // radio button for selecting gender
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.gender),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
                // male
                Row {
                    RadioButton(
                        selected = selectedGender == "Male", onClick = { selectedGender = "Male" }
                    )
                    Text(
                        text = stringResource(id = R.string.male),
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // female
                Row {
                    RadioButton(
                        selected = selectedGender == "Female",
                        onClick = { selectedGender = "Female" }
                    )
                    Text(
                        text = stringResource(id = R.string.female),
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }
            }
        }
    }
}
