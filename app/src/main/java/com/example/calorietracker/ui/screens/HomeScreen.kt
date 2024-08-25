package com.example.calorietracker.ui.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calorietracker.R
import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.ui.BottomNavBar
import com.example.calorietracker.ui.HomeScreenFAB
import com.example.calorietracker.ui.Screens
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.theme.backgroundDark
import com.example.calorietracker.ui.theme.backgroundLight
import com.example.calorietracker.ui.theme.onSurfaceDark
import com.example.calorietracker.ui.theme.onSurfaceLight
import com.example.calorietracker.ui.theme.onSurfaceVariantDark
import com.example.calorietracker.ui.theme.onSurfaceVariantLight
import com.example.calorietracker.ui.theme.onTertiaryContainerDark
import com.example.calorietracker.ui.theme.onTertiaryContainerLight
import com.example.calorietracker.ui.theme.primaryDark
import com.example.calorietracker.ui.theme.primaryLight
import com.example.calorietracker.ui.theme.secondaryDark
import com.example.calorietracker.ui.theme.secondaryLight
import com.example.calorietracker.ui.theme.surfaceVariantDark
import com.example.calorietracker.ui.theme.surfaceVariantLight
import com.example.calorietracker.ui.theme.tertiaryContainerDark
import com.example.calorietracker.ui.theme.tertiaryContainerLight
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onboardingViewModel: OnboardingViewModel,
    databaseViewModel: DatabaseViewModel,
    onAddButtonClicked: () -> Unit,
    onEditButtonClicked: (Meal, List<Ingredient>) -> Unit,
    onScreenChanged: (Screens) -> Unit
) {
    val mealUiState = databaseViewModel.mealUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    // state variable for showing name of the user
    var name by rememberSaveable {
        mutableStateOf("")
    }
    // state variable for showing total calories goal of the user
    var calorie by rememberSaveable {
        mutableStateOf("")
    }
    // state variable for showing total protein goal of the user
    var protein by rememberSaveable {
        mutableStateOf("")
    }
    // state variable for showing total carbs goal of the user
    var carbs by rememberSaveable {
        mutableStateOf("")
    }
    // state variable for showing total fat goal of the user
    var fat by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for showing completed calories by the user
    val completedCalorie by onboardingViewModel.completedCalorie.collectAsState()
    // state variable for showing completed protein by the user
    val completedProtein by onboardingViewModel.completedProtein.collectAsState()
    // state variable for showing completed carbs by the user
    val completedCarbs by onboardingViewModel.completedCarbs.collectAsState()
    // state variable for showing completed fat by the user
    val completedFat by onboardingViewModel.completedFat.collectAsState()

    // state variable for showing alert dialog when deleting a meal
    var showDeleteAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }
    // state variable for getting meal that the user wants to delete
    var mealToDelete by remember {
        mutableStateOf<Meal?>(null)
    }
    // state variable for getting ingredients that the user wants to delete
    var ingredientsForMealToDelete by remember {
        mutableStateOf<List<Ingredient>?>(null)
    }

    // getting the status bar height using window insets
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            HomeScreenFAB(
                onAddButtonClicked = {
                    onAddButtonClicked()
                }
            )
        },
        containerColor = if (isSystemInDarkTheme()) backgroundDark else backgroundLight,
        bottomBar = {
            BottomNavBar(
                selectedScreen = 0,
                onScreenChanged = { screen ->
                    onScreenChanged(screen)
                }
            )
        },
        topBar = {
            TopAppBar(
                title = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(systemBarsPadding.calculateTopPadding()),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) tertiaryContainerDark else tertiaryContainerLight
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    name = onboardingViewModel.getName()
                }
            }
            LaunchedEffect(Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    calorie = onboardingViewModel.getCalorie()
                }
            }
            LaunchedEffect(Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    protein = onboardingViewModel.getProtein()
                }
            }
            LaunchedEffect(Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    carbs = onboardingViewModel.getCarbs()
                }
            }
            LaunchedEffect(Unit) {
                coroutineScope.launch(Dispatchers.IO) {
                    fat = onboardingViewModel.getFat()
                }
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        tertiaryContainerDark
                    } else {
                        tertiaryContainerLight
                    }
                ),
                shape = RoundedCornerShape(
                    topEnd = 0.dp,
                    topStart = 0.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                GreetingText(
                    name = name,
                    modifier = Modifier.align(Alignment.Start)
                )

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier.align(Alignment.CenterVertically).padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        NutritionalProgressIndicators(
                            completedValue = completedCalorie.toFloatOrNull() ?: 0F,
                            totalValue = calorie,
                            color = Color(3, 252, 40),
                            modifier = Modifier
                                .size(200.dp)
                                .align(Alignment.Center)
                        )
                        NutritionalProgressIndicators(
                            completedValue = completedProtein.toFloatOrNull() ?: 0F,
                            totalValue = protein,
                            color = Color(254, 177, 24),
                            modifier = Modifier
                                .size(165.dp)
                                .align(Alignment.Center)
                        )
                        NutritionalProgressIndicators(
                            completedValue = completedCarbs.toFloatOrNull() ?: 0F,
                            totalValue = carbs,
                            color = Color(241, 255, 24),
                            modifier = Modifier
                                .size(130.dp)
                                .align(Alignment.Center)
                        )
                        NutritionalProgressIndicators(
                            completedValue = completedFat.toFloatOrNull() ?: 0F,
                            totalValue = fat,
                            color = Color(252, 84, 75),
                            modifier = Modifier
                                .size(95.dp)
                                .align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NutritionalInfo(
                            title = "Calorie",
                            completedValue = completedCalorie.toFloatOrNull() ?: 0F,
                            totalValue = calorie,
                            unit = "kcal",
                            Color(3, 252, 40)
                        )
                        NutritionalInfo(
                            title = "Protein",
                            completedValue = completedProtein.toFloatOrNull() ?: 0F,
                            totalValue = protein,
                            unit = "g",
                            Color(254, 177, 24)
                        )
                        NutritionalInfo(
                            title = "Carbs",
                            completedValue = completedCarbs.toFloatOrNull() ?: 0F,
                            totalValue = carbs,
                            unit = "g",
                            Color(241, 255, 24)
                        )
                        NutritionalInfo(
                            title = "Fat",
                            completedValue = completedFat.toFloatOrNull() ?: 0F,
                            totalValue = fat,
                            unit = "g",
                            Color(252, 84, 75)
                        )
                    }
                }
            }

            if (mealUiState.value.meals.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_meal_list)
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.your_meals),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
                    modifier = Modifier
                        .padding(start = 16.dp),
                    color = if (isSystemInDarkTheme()) primaryDark else primaryLight
                )
            }

            Column {
                mealUiState.value.meals.forEachIndexed { index, meal ->
                    val ingredients by databaseViewModel.getIngredientsForMeal(meal.mealID)
                        .collectAsState(emptyList())
                    MealCard(
                        modifier = if (index == (mealUiState.value.meals.size - 1)) Modifier.padding(bottom = 88.dp) else Modifier,
                        meal = meal,
                        ingredients = ingredients,
                        onMealCompleted = { completedMeal ->
                            databaseViewModel.updateMealCompletedStatus(completedMeal.mealID, true)

                            onboardingViewModel.updateCompletedCalorie(
                                ((completedCalorie.toIntOrNull()
                                    ?: 0) + completedMeal.totalCalorie.toInt())
                            )
                            onboardingViewModel.updateCompletedProtein(
                                ((completedProtein.toIntOrNull()
                                    ?: 0) + completedMeal.totalProtein.toInt())
                            )
                            onboardingViewModel.updateCompletedCarbs(
                                ((completedCarbs.toIntOrNull()
                                    ?: 0) + completedMeal.totalCarbs.toInt())
                            )
                            onboardingViewModel.updateCompletedFat(
                                ((completedFat.toIntOrNull() ?: 0) + completedMeal.totalFat.toInt())
                            )
                        },
                        onMealRemovedFromCompleted = { removedMeal ->
                            databaseViewModel.updateMealCompletedStatus(removedMeal.mealID, false)

                            onboardingViewModel.updateCompletedCalorie(
                                ((completedCalorie.toIntOrNull()
                                    ?: 0) - removedMeal.totalCalorie.toInt())
                            )
                            onboardingViewModel.updateCompletedProtein(
                                ((completedProtein.toIntOrNull()
                                    ?: 0) - removedMeal.totalProtein.toInt())
                            )
                            onboardingViewModel.updateCompletedCarbs(
                                ((completedCarbs.toIntOrNull()
                                    ?: 0) - removedMeal.totalCarbs.toInt())
                            )
                            onboardingViewModel.updateCompletedFat(
                                ((completedFat.toIntOrNull() ?: 0) - removedMeal.totalFat.toInt())
                            )
                        },
                        onDeleteButtonClicked = { deletedMeal, deletedIngredients ->
                            showDeleteAlertDialog = true
                            mealToDelete = deletedMeal
                            ingredientsForMealToDelete = deletedIngredients
                        },
                        onEditButtonClicked = { editedMeal, editedIngredients ->
                            onEditButtonClicked(editedMeal, editedIngredients)
                        }
                    )
                }
            }

            if (showDeleteAlertDialog) {
                // alert dialog for confirming that the user wants to delete the meal or not
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        Row {
                            // cancel button
                            TextButton(
                                onClick = {
                                    showDeleteAlertDialog = false
                                    mealToDelete = null
                                    ingredientsForMealToDelete = null
                                }
                            ) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            // delete button
                            TextButton(onClick = {
                                mealToDelete?.let { meal ->
                                    ingredientsForMealToDelete?.let { ingredients ->
                                        databaseViewModel.deleteMealAndIngredientsForMeal(
                                            meal = meal,
                                            ingredients = ingredients
                                        )
                                    }
                                }

                                if ((completedCalorie.toIntOrNull() ?: 0) != 0) {
                                    onboardingViewModel.updateCompletedCalorie(
                                        ((completedCalorie.toIntOrNull()
                                            ?: 0) - (mealToDelete?.totalCalorie?.toInt() ?: 0))
                                    )
                                }
                                if ((completedProtein.toIntOrNull() ?: 0) != 0) {
                                    onboardingViewModel.updateCompletedProtein(
                                        ((completedProtein.toIntOrNull()
                                            ?: 0) - (mealToDelete?.totalProtein?.toInt() ?: 0))
                                    )
                                }
                                if ((completedCarbs.toIntOrNull() ?: 0) != 0) {
                                    onboardingViewModel.updateCompletedCarbs(
                                        ((completedCarbs.toIntOrNull()
                                            ?: 0) - (mealToDelete?.totalCarbs?.toInt() ?: 0))
                                    )
                                }
                                if ((completedFat.toIntOrNull() ?: 0) != 0) {
                                    onboardingViewModel.updateCompletedFat(
                                        ((completedFat.toIntOrNull()
                                            ?: 0) - (mealToDelete?.totalFat?.toInt() ?: 0))
                                    )
                                }

                                showDeleteAlertDialog = false
                                mealToDelete = null
                                ingredientsForMealToDelete = null
                            }) {
                                Text(text = stringResource(id = R.string.delete))
                            }
                        }
                    },
                    text = {
                        Text(text = stringResource(id = R.string.delete_meal_text) + " ${mealToDelete?.mealName}?")
                    },
                    title = {
                        Text(text = stringResource(id = R.string.delete_meal))
                    }
                )
            }
        }
    }
}

// composable for showing nutritional goal in graphic format using circular progress indicator
@Composable
private fun NutritionalProgressIndicators(
    completedValue: Float,
    totalValue: String,
    color: Color,
    modifier: Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = completedValue,
        animationSpec = tween(durationMillis = 3000),
        label = ""
    )

    CircularProgressIndicator(
        progress = {
            if (totalValue.isEmpty()) {
                animatedProgress / 1F
            } else {
                animatedProgress / (totalValue.toFloatOrNull() ?: 1F)
            }
        },
        color = color,
        trackColor = if (isSystemInDarkTheme()) onTertiaryContainerDark else Color(0xFFF5F5F5),
        modifier = modifier,
        strokeWidth = 12.dp
    )
}

// composable for showing nutritional goals of the user
@SuppressLint("DefaultLocale")
@Composable
private fun NutritionalInfo(
    title: String,
    completedValue: Float,
    totalValue: String,
    unit: String,
    color: Color
) {
    val value by animateFloatAsState(
        targetValue = completedValue,
        label = "",
        animationSpec = tween(
            durationMillis = 2000
        )
    )

    Column(
        modifier = Modifier.padding(bottom = 4.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            color = if (isSystemInDarkTheme()) onTertiaryContainerDark else onTertiaryContainerLight
        )
        Text(
            text = "${String.format("%.0f", value)}/$totalValue $unit",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = color
        )
    }
}

// greeting text for the user
@Composable
private fun GreetingText(name: String, modifier: Modifier = Modifier) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (currentHour) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }
    Text(
        text = "$greeting, $name",
        fontSize = MaterialTheme.typography.displaySmall.fontSize,
        fontFamily = FontFamily(Font(R.font.dancingscript_bold)),
        lineHeight = 40.sp,
        modifier = modifier.padding(horizontal = 16.dp),
        color = if (isSystemInDarkTheme()) secondaryDark else secondaryLight,
    )
}

// composable for showing a meal
@Composable
fun MealCard(
    modifier: Modifier,
    meal: Meal,
    ingredients: List<Ingredient>,
    onMealCompleted: (Meal) -> Unit,
    onMealRemovedFromCompleted: (Meal) -> Unit,
    onDeleteButtonClicked: (Meal, List<Ingredient>) -> Unit,
    onEditButtonClicked: (Meal, List<Ingredient>) -> Unit
) {
    // state variable for check box
    var isChecked by remember {
        mutableStateOf(meal.isMealCompleted)
    }

    // using launched effect to trigger recomposition after editing a meal
    LaunchedEffect(meal.isMealCompleted) {
        isChecked = meal.isMealCompleted
    }

    // state variable for showing ingredients
    var showIngredients by rememberSaveable {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSystemInDarkTheme()) surfaceVariantDark else surfaceVariantLight
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = meal.mealName,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isSystemInDarkTheme()) onSurfaceVariantDark else onSurfaceVariantLight
                )
                Modifier.weight(1f)
                Row {
                    // delete button
                    IconButton(onClick = { onDeleteButtonClicked(meal, ingredients) }) {
                        Image(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete),
                            colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight) // Updated color
                        )
                    }
                    // edit button
                    IconButton(onClick = { onEditButtonClicked(meal, ingredients) }) {
                        Image(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight) // Updated color
                        )
                    }
                    // checkbox for checking if the meal is completed or not
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = {
                            isChecked = !isChecked
                            if (isChecked) {
                                onMealCompleted(meal)
                            } else {
                                onMealRemovedFromCompleted(meal)
                            }
                        }
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // calorie display
                MealNutritionalInfo(
                    value = meal.totalCalorie,
                    unit = R.string.calorie_unit,
                    valueFontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    unitFontSize = MaterialTheme.typography.titleMedium.fontSize,
                    nutritionType = "Calorie",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Row {
                    // protein display
                    MealNutritionalInfo(
                        value = meal.totalProtein,
                        unit = R.string.gram_unit,
                        valueFontSize = MaterialTheme.typography.titleLarge.fontSize,
                        unitFontSize = MaterialTheme.typography.titleSmall.fontSize,
                        nutritionType = "Protein"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // carbs display
                    MealNutritionalInfo(
                        value = meal.totalCarbs,
                        unit = R.string.gram_unit,
                        valueFontSize = MaterialTheme.typography.titleLarge.fontSize,
                        unitFontSize = MaterialTheme.typography.titleSmall.fontSize,
                        nutritionType = "Carbs"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    // fat display
                    MealNutritionalInfo(
                        value = meal.totalFat,
                        unit = R.string.gram_unit,
                        valueFontSize = MaterialTheme.typography.titleLarge.fontSize,
                        unitFontSize = MaterialTheme.typography.titleSmall.fontSize,
                        nutritionType = "Fat"
                    )
                }

                // button for expanding and closing the meal to show ingredients
                if (showIngredients) {
                    IconButton(
                        onClick = { showIngredients = false },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Image(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = stringResource(id = R.string.close_expanded_meal),
                            colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showIngredients = true },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Image(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(id = R.string.expand_meal),
                            colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = showIngredients) {
                Column {
                    ingredients.forEach { ingredient ->
                        Row(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = ingredient.name,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Row(
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = ingredient.quantity
                                    )
                                    Text(
                                        text = stringResource(id = R.string.bullet_point),
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                    Text(
                                        text = "${ingredient.calories} " + stringResource(id = R.string.calorie_unit)
                                    )
                                }
                            }
                            Modifier.weight(1f)
                            Row {
                                IngredientNutritionalInfo(
                                    value = ingredient.protein,
                                    unit = R.string.gram_unit,
                                    nutritionType = "Protein"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IngredientNutritionalInfo(
                                    value = ingredient.carbs,
                                    unit = R.string.gram_unit,
                                    nutritionType = "Carbs"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                IngredientNutritionalInfo(
                                    value = ingredient.fat,
                                    unit = R.string.gram_unit,
                                    nutritionType = "Fat"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// composable for showing nutrient nutritional info
@Composable
fun IngredientNutritionalInfo(
    value: String,
    @StringRes unit: Int,
    nutritionType: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = value,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Text(
                text = " " + stringResource(id = unit),
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
        Text(
            text = nutritionType,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = MaterialTheme.typography.titleSmall.fontSize
        )
    }
}

// composable for showing meal nutritional info
@Composable
fun MealNutritionalInfo(
    value: String,
    @StringRes unit: Int,
    valueFontSize: TextUnit,
    unitFontSize: TextUnit,
    nutritionType: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row {
            Text(
                text = value,
                fontSize = valueFontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Bottom)
            )
            Text(
                text = " " + stringResource(id = unit),
                fontSize = unitFontSize,
                modifier = Modifier.align(Alignment.Bottom)
            )
        }
        if (nutritionType != "Calorie") {
            Text(
                text = nutritionType,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            )
        }
    }
}