package com.example.calorietracker.ui.onboarding.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.calorietracker.R
import com.example.calorietracker.ui.onboarding.OnboardingViewModel

// first screen for the onboarding process
@Composable
fun FirstScreen(
    modifier: Modifier,
    onNextButtonClicked: () -> Unit = {},
    onboardingViewModel: OnboardingViewModel = hiltViewModel()
) {
    // state variable for lottie animation composition
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.first_screen))

    // state variable for name
    var name by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for age
    var age by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for gender
    var selectedGender by rememberSaveable {
        mutableStateOf("Male")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(320.dp),
            iterations = LottieConstants.IterateForever
        )
        Text(
            text = stringResource(id = R.string.welcome),
            fontSize = MaterialTheme.typography.displayLarge.fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = stringResource(id = R.string.welcome_text),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = FontFamily(Font(R.font.dancingscript_regular)),
            modifier = Modifier.padding(8.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // name text field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                singleLine = true,
                label = {
                    Text(text = stringResource(id = R.string.name))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            // age text field
            OutlinedTextField(
                value = age,
                onValueChange = { value ->
                    age = value.filter {        // making sure that the user enter only integer value and not a decimal value or any whitespaces
                        it.isDigit()
                    }
                },
                label = {
                    Text(text = stringResource(id = R.string.age))
                },
                singleLine = true,
                modifier = Modifier.padding(top = 16.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            )

            // Radio buttons for selecting gender
            Row(
                modifier = Modifier.padding(top = 16.dp)
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

        Spacer(modifier = Modifier.weight(1f))

        // Button for navigation in the onboarding screen
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onNextButtonClicked()
                    onboardingViewModel.updateName(name)
                    onboardingViewModel.updateAge(age.toInt())
                    onboardingViewModel.updateGender(gender = selectedGender)
                },
                enabled = name.isNotBlank() && age.isNotBlank()
            ) {
                Text(text = stringResource(id = R.string.next))
            }
        }
    }
}