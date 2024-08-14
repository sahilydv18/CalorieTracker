package com.example.calorietracker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.calorietracker.R
import java.util.Calendar

// top app bar for home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(name: String) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    TopAppBar(
        title = {
            Text(
                text = "$greeting, $name",
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
            )
        }
    )
}

// FAB for home screen
@Composable
fun HomeScreenFAB(
    onAddButtonClicked: () -> Unit
) {
    FloatingActionButton(onClick = { onAddButtonClicked() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_meal)
        )
    }
}

// top app bar for meal adding screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealAddingScreenTopAppBar(
    onBackButtonClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.meal))
        },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked() }) {
                if (isSystemInDarkTheme()) {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                } else {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        }
    )
}