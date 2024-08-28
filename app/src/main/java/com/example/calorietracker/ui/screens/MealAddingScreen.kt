package com.example.calorietracker.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.calorietracker.R
import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.database.Meal
import com.example.calorietracker.ui.MealAddingScreenTopAppBar
import com.example.calorietracker.ui.onboarding.OnboardingViewModel
import com.example.calorietracker.ui.theme.backgroundDark
import com.example.calorietracker.ui.theme.backgroundLight
import com.example.calorietracker.ui.theme.onPrimaryDark
import com.example.calorietracker.ui.theme.onPrimaryLight
import com.example.calorietracker.ui.theme.onSecondaryContainerDark
import com.example.calorietracker.ui.theme.onSecondaryContainerLight
import com.example.calorietracker.ui.theme.onSurfaceDark
import com.example.calorietracker.ui.theme.onSurfaceLight
import com.example.calorietracker.ui.theme.primaryDark
import com.example.calorietracker.ui.theme.primaryLight
import com.example.calorietracker.ui.theme.secondaryContainerDark
import com.example.calorietracker.ui.theme.secondaryContainerLight
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel
import com.example.calorietracker.ui.viewmodel.IngredientApiViewModel
import com.example.calorietracker.ui.viewmodel.IngredientItem
import com.example.calorietracker.ui.viewmodel.toIngredient
import com.example.calorietracker.ui.viewmodel.toIngredientItem

// screen for adding a meal
@Composable
fun MealAddingScreen(
    onCancelButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    databaseViewModel: DatabaseViewModel,
    onboardingViewModel: OnboardingViewModel,
    onAddButtonClicked: () -> Unit,
    meal: Meal?,
    ingredient: List<Ingredient>?,
    ingredientApiViewModel: IngredientApiViewModel
) {

    // list for showing ingredients that the user adds
    val mealIngredients = remember {
        mutableStateListOf<IngredientItem>()
    }

    // adding ingredient to the list when user is editing a meal
    // using launched effect to make sure that the ingredients are added only once
    LaunchedEffect(ingredient) {
        ingredient?.let {
            mealIngredients.clear() // Clear any existing ingredients
            mealIngredients.addAll(it.map { ingredient -> ingredient.toIngredientItem() })
        }
    }

    // state variable for showing completed calories by the user
    val completedCalorie by onboardingViewModel.completedCalorie.collectAsState()
    // state variable for showing completed protein by the user
    val completedProtein by onboardingViewModel.completedProtein.collectAsState()
    // state variable for showing completed carbs by the user
    val completedCarbs by onboardingViewModel.completedCarbs.collectAsState()
    // state variable for showing completed fat by the user
    val completedFat by onboardingViewModel.completedFat.collectAsState()

    // state variable for showing ingredient adding dialog
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    // state variable for meal name
    var mealName by rememberSaveable {
        mutableStateOf(meal?.mealName ?: "")
    }

    // state variable for showing total calorie of a meal
    var totalCalorie by rememberSaveable {
        mutableStateOf(meal?.totalCalorie ?: "0")
    }

    // state variable for showing total protein of a meal
    var totalProtein by rememberSaveable {
        mutableStateOf(meal?.totalProtein ?: "0")
    }

    // state variable for showing total carbs of a meal
    var totalCarbs by rememberSaveable {
        mutableStateOf(meal?.totalCarbs ?: "0")
    }

    // state variable for showing total fat of a meal
    var totalFat by rememberSaveable {
        mutableStateOf(meal?.totalFat ?: "0")
    }

    // state variable for ingredient to add so that I can use it to prepopulate text field while editing the element
    var ingredientToAdd by remember {
        mutableStateOf(
            IngredientItem(
                name = "",
                quantity = "",
                calories = "",
                protein = "",
                fat = "",
                carbs = ""
            ).toIngredient()
        )
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MealAddingScreenTopAppBar(
                onBackButtonClicked = {
                    onBackButtonClicked()
                },
                title = if (meal != null) R.string.edit_meal else R.string.meal
            )
        },
        bottomBar = {
            // conformation and cancellation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // cancel button
                OutlinedButton(onClick = { onCancelButtonClicked() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Modifier.weight(1f)
                // add button
                Button(
                    onClick = {
                        if (meal != null && ingredient != null) {
                            // editing an existing meal
                            if (meal.isMealCompleted) {
                                onboardingViewModel.updateCompletedCalorie(
                                    ((completedCalorie.toIntOrNull() ?: 0) - meal.totalCalorie.toInt())
                                )
                                onboardingViewModel.updateCompletedProtein(
                                    ((completedProtein.toIntOrNull() ?: 0) - meal.totalProtein.toInt())
                                )
                                onboardingViewModel.updateCompletedCarbs(
                                    ((completedCarbs.toIntOrNull() ?: 0) - meal.totalCarbs.toInt())
                                )
                                onboardingViewModel.updateCompletedFat(
                                    ((completedFat.toIntOrNull() ?: 0) - meal.totalFat.toInt())
                                )
                            }

                            databaseViewModel.updateMealAndIngredients(
                                updatedMeal = Meal(
                                    mealID = meal.mealID,
                                    mealName = mealName,
                                    totalCalorie = totalCalorie,
                                    totalProtein = totalProtein,
                                    totalCarbs = totalCarbs,
                                    totalFat = totalFat,
                                    isMealCompleted = false
                                ),
                                updatedIngredients = mealIngredients,
                                oldIngredients = ingredient
                            )
                        } else {
                            // adding a new meal
                            databaseViewModel.insertMealAndIngredientsForMeal(
                                meal = Meal(
                                    mealName = mealName,
                                    totalCalorie = totalCalorie,
                                    totalProtein = totalProtein,
                                    totalCarbs = totalCarbs,
                                    totalFat = totalFat
                                ),
                                mealIngredients = mealIngredients
                            )
                        }
                        onAddButtonClicked()
                    },
                    enabled = mealIngredients.isNotEmpty() && mealName.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) primaryDark else primaryLight,
                        contentColor = if (isSystemInDarkTheme()) onPrimaryDark else onPrimaryLight
                    )
                ) {
                    Row {
                        Image(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (meal != null) stringResource(id = R.string.save) else stringResource(
                                id = R.string.add
                            ),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        },
        containerColor = if (isSystemInDarkTheme()) backgroundDark else backgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // text field for entering meal name
            OutlinedTextField(
                value = mealName,
                onValueChange = {
                    mealName = it
                },
                label = {
                    Text(text = stringResource(id = R.string.meal_name))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            // Nutritional info for a meal
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    NutritionalInfo(
                        title = R.string.total_calorie,
                        value = totalCalorie,
                        unit = R.string.calorie_unit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    NutritionalInfo(
                        title = R.string.total_carbs,
                        value = totalCarbs,
                        unit = R.string.gram_unit
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    NutritionalInfo(
                        title = R.string.total_protein,
                        value = totalProtein,
                        unit = R.string.gram_unit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    NutritionalInfo(
                        title = R.string.total_fat,
                        value = totalFat,
                        unit = R.string.gram_unit
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // button to add ingredients to a meal
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors( // Set button colors
                    containerColor = if (isSystemInDarkTheme()) secondaryContainerDark else secondaryContainerLight,
                    contentColor = if (isSystemInDarkTheme()) onSecondaryContainerDark else onSecondaryContainerLight
                )
            ) {
                Row {
                    Image(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.add_ingredient),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // lazy column to display the list of ingredients present in a meal
            LazyColumn {
                if (mealIngredients.isEmpty()) {
                    item {
                        Text(text = stringResource(id = R.string.empty_ingredient_list))
                    }
                } else {
                    // Convert the entire list to Ingredient objects
                    val ingredients = mealIngredients.map { it.toIngredient() }
                    items(ingredients) {
                        IngredientCard(
                            ingredient = it,
                            onEditButtonClicked = { ingredient ->
                                showDialog = true
                                ingredientToAdd = ingredient
                            },
                            onDeleteButtonClicked = { ingredient ->
                                mealIngredients.remove(ingredient.toIngredientItem())

                                // updating the nutritional values for meal when ingredient is removed
                                totalCalorie =
                                    (totalCalorie.toInt() - ingredient.calories.toInt()).toString()
                                totalProtein =
                                    (totalProtein.toInt() - ingredient.protein.toInt()).toString()
                                totalCarbs =
                                    (totalCarbs.toInt() - ingredient.carbs.toInt()).toString()
                                totalFat = (totalFat.toInt() - ingredient.fat.toInt()).toString()
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        IngredientAddingScreen(
            dismissDialog = {
                showDialog = false

                // resetting the ingredient value to empty string after an element has been added, edited or editing is canceled so that the text field don't prepopulate when user adds
                // a new ingredient
                ingredientToAdd = IngredientItem(
                    name = "",
                    quantity = "",
                    calories = "",
                    protein = "",
                    fat = "",
                    carbs = ""
                ).toIngredient()
            },
            onIngredientAdded = { newIngredient ->
                if (mealIngredients.contains(ingredientToAdd.toIngredientItem())) {     // checking if the list already contains the ingredient
                    mealIngredients.remove(ingredientToAdd.toIngredientItem())          // removing the old ingredient

                    // updating the nutritional values for meal when ingredient is updated, subtracting the calories of previous ingredient
                    totalCalorie = (totalCalorie.toInt() - ingredientToAdd.calories.toInt()).toString()
                    totalProtein = (totalProtein.toInt() - ingredientToAdd.protein.toInt()).toString()
                    totalCarbs = (totalCarbs.toInt() - ingredientToAdd.carbs.toInt()).toString()
                    totalFat = (totalFat.toInt() - ingredientToAdd.fat.toInt()).toString()

                    mealIngredients.add(newIngredient)                                  // adding the new ingredient
                } else {
                    mealIngredients.add(newIngredient)                                  // directly adding the new ingredient if the list doesn't contain the ingredient
                }
                // updating the nutritional values for meal when ingredient is added
                totalCalorie = (totalCalorie.toInt() + newIngredient.calories.toInt()).toString()
                totalProtein = (totalProtein.toInt() + newIngredient.protein.toInt()).toString()
                totalCarbs = (totalCarbs.toInt() + newIngredient.carbs.toInt()).toString()
                totalFat = (totalFat.toInt() + newIngredient.fat.toInt()).toString()
            },
            ingredient = ingredientToAdd,
            ingredientApiViewModel = ingredientApiViewModel
        )
    }
}

// card for displaying information about an ingredient
@Composable
private fun IngredientCard(
    ingredient: Ingredient,
    onEditButtonClicked: (Ingredient) -> Unit,
    onDeleteButtonClicked: (Ingredient) -> Unit
) {
    Card(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = ingredient.name,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Quantity: ${ingredient.quantity}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row {
                    // edit icon button to edit an existing ingredient
                    IconButton(onClick = {
                        onEditButtonClicked(ingredient)
                    }) {
                        if (isSystemInDarkTheme()) {
                            Image(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(id = R.string.edit),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        } else {
                            Image(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(id = R.string.edit)
                            )
                        }
                    }
                    // delete icon button to delete an existing ingredient
                    IconButton(onClick = { onDeleteButtonClicked(ingredient) }) {
                        if (isSystemInDarkTheme()) {
                            Image(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        } else {
                            Image(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete)
                            )
                        }
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (isSystemInDarkTheme()) onSurfaceDark else onSurfaceLight
            )
            Row {
                Column {
                    NutritionalInfo(
                        title = R.string.calorie_ingredient,
                        value = ingredient.calories,
                        unit = R.string.calorie_unit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    NutritionalInfo(
                        title = R.string.carbs_ingredient,
                        value = ingredient.carbs,
                        unit = R.string.gram_unit
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    NutritionalInfo(
                        title = R.string.protein_ingredient,
                        value = ingredient.protein,
                        unit = R.string.gram_unit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    NutritionalInfo(
                        title = R.string.fat_ingredient,
                        value = ingredient.fat,
                        unit = R.string.gram_unit
                    )
                }
            }
        }
    }
}

// composable for displaying nutritional info
@Composable
private fun NutritionalInfo(@StringRes title: Int, value: String, @StringRes unit: Int) {
    Row {
        Text(
            text = stringResource(id = title),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value " + stringResource(id = unit),
            fontSize = MaterialTheme.typography.titleMedium.fontSize
        )
    }
}