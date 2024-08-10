package com.example.calorietracker.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.calorietracker.R

// third screen for onboarding process
@Composable
fun ThirdScreen() {

    // state variable for lottie animation composition
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.third_screen))

    // state variable for calorie
    var calorie by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for protein
    var protein by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for carbs
    var carbs by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for fat
    var fat by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // calorie text field
            OutlinedTextField(
                value = calorie,
                onValueChange = {
                    calorie = it
                },
                label = {
                    Text(text = "Calorie (in kcal)")
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
                onValueChange = {
                    protein = it
                },
                label = {
                    Text(text = "Protein (in g)")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            // carbs text field
            OutlinedTextField(
                value = carbs,
                onValueChange = {
                    carbs = it
                },
                label = {
                    Text(text = "Carbs (in g)")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            // fat text field
            OutlinedTextField(
                value = fat,
                onValueChange = {
                    fat = it
                },
                label = {
                    Text(text = "Fat (in g)")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
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
                enabled = calorie.isNotBlank() && protein.isNotBlank() && carbs.isNotBlank() && fat.isNotBlank()
            ) {
                Text(text = stringResource(id = R.string.finish))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ThirdScreenPreview() {
    ThirdScreen()
}