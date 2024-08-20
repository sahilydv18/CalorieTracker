package com.example.calorietracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.calorietracker.ui.BottomNavBar
import com.example.calorietracker.ui.Screens

// composable for settings screen, here user can change app settings
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}