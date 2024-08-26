package com.example.calorietracker.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorietracker.R
import com.example.calorietracker.ui.BottomNavBar
import com.example.calorietracker.ui.Screens
import com.example.calorietracker.ui.theme.backgroundDark
import com.example.calorietracker.ui.theme.backgroundLight
import com.example.calorietracker.ui.theme.onPrimaryContainerDark
import com.example.calorietracker.ui.theme.onPrimaryContainerLight
import com.example.calorietracker.ui.theme.onSurfaceDark
import com.example.calorietracker.ui.theme.onSurfaceLight
import com.example.calorietracker.ui.theme.primaryContainerDark
import com.example.calorietracker.ui.theme.primaryContainerLight

// composable for settings screen, here user can change app settings
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onScreenChanged: (Screens) -> Unit,
    onEditButtonClicked: (Screens) -> Unit
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
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) primaryContainerDark else primaryContainerLight,
                    titleContentColor = if (isSystemInDarkTheme()) onPrimaryContainerDark else onPrimaryContainerLight
                )
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
            // section for editing personal info
            EditingSection(
                title = R.string.edit_personal_info,
                titleInfo = R.string.edit_personal_info_supporting_text,
                onEditButtonClicked = { screen ->
                    onEditButtonClicked(screen)
                },
                screen = Screens.EDIT_PERSONAL_INFO_SCREEN
            )
            // section for editing biometric info
            EditingSection(
                title = R.string.edit_biometric_info,
                titleInfo = R.string.edit_biometric_info_supporting_text,
                onEditButtonClicked = { screen ->
                    onEditButtonClicked(screen)
                },
                screen = Screens.EDIT_BIOMETRIC_INFO_SCREEN
            )
            // section for editing nutritional info
            EditingSection(
                title = R.string.edit_nutritional_info,
                titleInfo = R.string.edit_nutritional_info_supporting_text,
                onEditButtonClicked = { screen ->
                    onEditButtonClicked(screen)
                },
                screen = Screens.EDIT_NUTRITIONAL_INFO_SCREEN
            )
            // about section
            AboutSection()
        }
    }
}

@Composable
fun EditingSection(
    @StringRes title: Int,
    @StringRes titleInfo: Int,
    onEditButtonClicked: (Screens) -> Unit,
    screen: Screens
) {
    Card(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = title),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = titleInfo),
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onEditButtonClicked(screen) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.edit))
            }
        }
    }
}

// about me section
@Composable
fun AboutSection() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
    )

    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.about),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight
            )
            Text(
                text = stringResource(id = R.string.app_name_and_version),
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.made_by_text),
                fontSize = MaterialTheme.typography.titleSmall.fontSize
            )
            Row {
                Text(
                    text = stringResource(id = R.string.connect_with_me),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                )
                // email
                IconButton(onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "vnd.android.cursor.item/email"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("ydvvsahil09@gmail.com"))
                        }
                        launcher.launch(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "No email app found", Toast.LENGTH_LONG).show()
                    } catch (t: Throwable) {
                        Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Image(
                        //painter = painterResource(id = R.drawable.envelope_24),
                        imageVector = Icons.Default.Email,
                        modifier = Modifier.size(28.dp),
                        contentDescription = "email",
                        colorFilter = ColorFilter.tint( if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight)
                    )
                }
                // github
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://github.com/sahilydv18")
                    }
                    launcher.launch(intent)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.github_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint( if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight)
                    )
                }
                // linkedIn
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://www.linkedin.com/in/ydvsahil18")
                    }
                    launcher.launch(intent)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint( if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight)
                    )
                }
                // twitter (x)
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://x.com/sahil_yadvv")
                    }
                    launcher.launch(intent)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.twitter_alt_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint( if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight)
                    )
                }
            }
        }
    }
}