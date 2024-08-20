package com.example.calorietracker.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.calorietracker.R
import com.example.calorietracker.ui.BottomNavBar
import com.example.calorietracker.ui.Screens
import com.example.calorietracker.ui.theme.backgroundDark
import com.example.calorietracker.ui.theme.backgroundLight

// composable for settings screen, here user can change app settings
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onScreenChanged: (Screens) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(
                selectedScreen = 1,
                onScreenChanged = { screen ->
                    onScreenChanged(screen)
                }
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings),
                        fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )
                }
            )
        },
        containerColor = if (isSystemInDarkTheme()) backgroundDark else backgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

        }
    }
}