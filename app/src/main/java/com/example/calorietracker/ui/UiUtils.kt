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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.calorietracker.R
import com.example.calorietracker.ui.theme.onPrimaryContainerDark
import com.example.calorietracker.ui.theme.onPrimaryContainerLight
import com.example.calorietracker.ui.theme.onSecondaryContainerDark
import com.example.calorietracker.ui.theme.onSecondaryContainerLight
import com.example.calorietracker.ui.theme.primaryContainerDark
import com.example.calorietracker.ui.theme.primaryContainerLight
import com.example.calorietracker.ui.theme.secondaryContainerDark
import com.example.calorietracker.ui.theme.secondaryContainerLight

// FAB for home screen
@Composable
fun HomeScreenFAB(
    onAddButtonClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = { onAddButtonClicked() },
        containerColor = if (isSystemInDarkTheme()) secondaryContainerDark else secondaryContainerLight,
        contentColor = if (isSystemInDarkTheme()) onSecondaryContainerDark else onSecondaryContainerLight
    ) {
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
                        colorFilter = ColorFilter.tint(onPrimaryContainerDark)
                    )
                } else {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        colorFilter = ColorFilter.tint(onPrimaryContainerLight)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = if (isSystemInDarkTheme()) primaryContainerDark else primaryContainerLight,
            titleContentColor = if (isSystemInDarkTheme()) onPrimaryContainerDark else onPrimaryContainerLight
        )
    )
}

// bottom nav bar
@Composable
fun BottomNavBar(
    onScreenChanged: (Screens) -> Unit,
    selectedScreen: Int
) {

    var selectedItem by remember { mutableIntStateOf(selectedScreen) }

    val items = listOf(
        Screens.HOME_SCREEN,
        Screens.ACCOUNT_SCREEN
    )

    val selectedIcon = listOf(
        R.drawable.baseline_home_24,
        R.drawable.baseline_manage_accounts_24
    )

    val unselectedIcons = listOf(
        R.drawable.outline_home_24,
        R.drawable.outline_manage_accounts_24
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onScreenChanged(items[index])
                },
                icon = {
                    Icon(
                        painter = painterResource(id = if (selectedItem == index) selectedIcon[index] else unselectedIcons[index]),
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = if (item == Screens.HOME_SCREEN) R.string.home else R.string.settings)
                    )
                }
            )
        }
    }
}