package com.example.calorietracker.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.calorietracker.R

// second screen for the onboarding process
@Composable
fun SecondScreen() {

    // state variable for lottie animation composition
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.second_screen))

    // state variable for weight
    var weight by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for height
    var height by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    // list of different exercise levels
    val exerciseLevels = context.resources.getStringArray(R.array.exercise_list)

    // state variable for expansion of dropdown menu
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    // state variable for showing the selected exercise level in the outlined text field
    var selectedLevel by rememberSaveable {
        mutableStateOf(exerciseLevels[0])
    }

    // state variable for goal
    var selectedGoal by rememberSaveable {
        mutableStateOf("Weight Loss")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(360.dp),
            iterations = LottieConstants.IterateForever
        )
        Text(
            text = stringResource(id = R.string.screen_two_text),
            fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // weight text field
            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it
                },
                label = {
                    Text(text = stringResource(id = R.string.weight))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )

            // height text field
            OutlinedTextField(
                value = height,
                onValueChange = {
                    height = it
                },
                label = {
                    Text(text = stringResource(id = R.string.height))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // dropdown menu for choosing exercise level
        Box {
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
                modifier = Modifier.width(282.dp)
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

        // radio buttons for choosing weight gain or loose
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.goal),
                modifier = Modifier.align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
            // weight loss
            Row {
                RadioButton(selected = selectedGoal == "Weight Loss", onClick = { selectedGoal = "Weight Loss" })
                Text(
                    text = stringResource(id = R.string.weight_loss),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            // weight gain
            Row {
                RadioButton(selected = selectedGoal == "Weight Gain", onClick = { selectedGoal = "Weight Gain" })
                Text(
                    text = stringResource(id = R.string.weight_gain),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            }
        }

        // buttons for navigating the onboarding screens
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.previous))
            }
            Modifier.weight(1f)
            Button(
                onClick = { /*TODO*/ },
                enabled = weight.isNotBlank() && height.isNotBlank()
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SecondScreenPreview() {
    SecondScreen()
}