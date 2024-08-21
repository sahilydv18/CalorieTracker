package com.example.calorietracker.ui.screens.editing

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
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
import com.example.calorietracker.ui.onboarding.screen.getPAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BiometricInfoEditScreen(
    onBackButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    onboardingViewModel: OnboardingViewModel,
    onSaveButtonClicked: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    // state variable for weight
    var weight by remember {
        mutableStateOf("")
    }

    // state variable for height
    var height by remember {
        mutableStateOf("")
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

    val context = LocalContext.current

    // list of different exercise levels
    val exerciseLevels = context.resources.getStringArray(R.array.exercise_list)

    // state variable for expansion of exercise list dropdown menu
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    // state variable for getting pal
    var pal by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            pal = onboardingViewModel.getPAL()
        }
    }

    // state variable for showing the selected exercise level in the outlined text field
    var selectedLevel by rememberSaveable {
        mutableStateOf(exerciseLevels[0])
    }

    LaunchedEffect(pal) {
        if (pal.isNotBlank()) {
            selectedLevel = getExerciseLevel(pal.toDouble(), context)
        }
    }

    // list of different weight goals
    val goalList = context.resources.getStringArray(R.array.weight_goal_list)

    // state variable for expansion of weight goal dropdown menu
    var goalExpanded by rememberSaveable {
        mutableStateOf(false)
    }


    // state variable for getting weight goal
    var weightGoal by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            weightGoal = onboardingViewModel.getWeightGoal()
        }
    }

    // state variable for showing the selected weight goal in the outlined text field
    var selectedGoal by rememberSaveable {
        mutableStateOf(goalList[2])
    }

    LaunchedEffect(weightGoal) {
        if (weightGoal.isNotBlank()) {
            selectedGoal = weightGoal
        }
    }

    val age by produceState(initialValue = "") {
        value = onboardingViewModel.getAge()
    }

    val gender by produceState(initialValue = "") {
        value = onboardingViewModel.getGender()
    }

    Scaffold(
        topBar = {
            EditingScreenTopAppBar(title = R.string.edit_biometric_info) {
                onBackButtonClicked()
            }
        },
        bottomBar = {
            EditingScreenBottomAppBar(
                onCancelButtonClicked = { onCancelButtonClicked() },
                onSaveButtonClicked = {

                    val calorie = NutritionalCalculation.calculateCalories(
                        pal = getPAL(selectedLevel),
                        weight = weight.toDouble(),
                        height = height.toDouble(),
                        age = age.toDouble(),
                        gender = gender,
                        weightGoal = selectedGoal
                    )

                    val protein = NutritionalCalculation.calculateProtein(weight = weight.toDouble())

                    val fat = NutritionalCalculation.calculateFat(calorie.toDouble())

                    onboardingViewModel.updateWeight(weight.toDouble())
                    onboardingViewModel.updateHeight(height.toDouble())
                    onboardingViewModel.updatePAL(getPAL(selectedLevel))
                    onboardingViewModel.updateWeightGoal(selectedGoal)

                    onboardingViewModel.updateCalorie(calorie.toInt())
                    onboardingViewModel.updateProtein(protein.toInt())
                    onboardingViewModel.updateFat(fat.toInt())
                    onboardingViewModel.updateCarbs(
                        NutritionalCalculation.calculateCarbs(
                            calorie = calorie.toDouble(),
                            fat = fat.toDouble(),
                            protein = protein.toDouble()
                        ).toInt()
                    )

                    onSaveButtonClicked()
                },
                saveButtonEnabled = weight.isNotBlank() && height.isNotBlank()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.biometric_info),
                contentDescription = stringResource(id = R.string.edit_biometric_info),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .size(300.dp)
            )

            // weight text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.weight),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = weight,
                    onValueChange = { value ->      // making sure that the user can only enter valid integer numbers or decimal numbers up to 2 places and not any whitespaces
                        weight = value.filter {
                            it.isDigit() || it == '.'
                        }.replace(
                            Regex("(\\.[0-9]{2}).*"), "$1"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // height text field
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.height),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = height,
                    onValueChange = { value ->
                        height = value.filter {     // making sure that the user can only enter valid integer numbers or decimal numbers up to 2 places and not any whitespaces
                            it.isDigit() || it == '.'
                        }.replace(
                            Regex("(\\.[0-9]{2}).*"), "$1"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // dropdown menu for choosing exercise level
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = selectedLevel,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(text = stringResource(id = R.string.exercise_level))
                    },
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = stringResource(id = R.string.dropdown_reason)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    scrollState = rememberScrollState()
                ) {
                    exerciseLevels.forEach {
                        DropdownMenuItem(
                            modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp),
                            text = { Text(text = it) },
                            onClick = {
                                selectedLevel = it
                                expanded = false
                            })
                    }
                }
            }

            // dropdown menu for selecting weight goal
            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                OutlinedTextField(
                    value = selectedGoal,
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    label = {
                        Text(text = stringResource(id = R.string.goal))
                    },
                    trailingIcon = {
                        IconButton(onClick = { goalExpanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = stringResource(id = R.string.dropdown_goal_reason)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = goalExpanded,
                    onDismissRequest = { goalExpanded = false },
                    scrollState = rememberScrollState()
                ) {
                    goalList.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                selectedGoal = it
                                goalExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

fun getExerciseLevel(pal: Double, context: Context): String {
    // list of different exercise levels
    val exerciseLevels = context.resources.getStringArray(R.array.exercise_list)
    val exerciseLevel = when(pal) {
        1.2 -> exerciseLevels[0]
        1.4 -> exerciseLevels[1]
        1.6 -> exerciseLevels[2]
        1.75 -> exerciseLevels[3]
        2.0 -> exerciseLevels[4]
        2.3 -> exerciseLevels[5]
        else -> ""
    }
    return exerciseLevel
}