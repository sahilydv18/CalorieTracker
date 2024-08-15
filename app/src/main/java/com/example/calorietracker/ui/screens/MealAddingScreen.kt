package com.example.calorietracker.ui.screens

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.calorietracker.database.MealIngredients
import com.example.calorietracker.ui.MealAddingScreenTopAppBar
import com.example.calorietracker.ui.viewmodel.DatabaseViewModel
import com.example.calorietracker.ui.viewmodel.IngredientItem
import com.example.calorietracker.ui.viewmodel.toIngredient
import com.example.calorietracker.ui.viewmodel.toIngredientItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// screen for adding a meal
@Composable
fun MealAddingScreen(
    onCancelButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    databaseViewModel: DatabaseViewModel
) {

    // list for showing ingredients that the user adds
    val mealIngredients = remember {
        mutableStateListOf<IngredientItem>()
    }

    // counter for ingredient IDs
    var nextIngredientId by remember {
        mutableStateOf(1)
    }

    // state variable for showing ingredient adding dialog
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    // state variable for meal name
    var mealName by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for showing total calorie of a meal
    var totalCalorie by rememberSaveable {
        mutableStateOf("0")
    }

    // state variable for showing total protein of a meal
    var totalProtein by rememberSaveable {
        mutableStateOf("0")
    }

    // state variable for showing total carbs of a meal
    var totalCarbs by rememberSaveable {
        mutableStateOf("0")
    }

    // state variable for showing total fat of a meal
    var totalFat by rememberSaveable {
        mutableStateOf("0")
    }

    // ingredient to add
    var ingredientToAdd = remember {
        IngredientItem(
            name = "",
            quantity = "",
            calories = "",
            protein = "",
            fat = "",
            carbs = ""
        ).toIngredient()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MealAddingScreenTopAppBar {
                onBackButtonClicked()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 8.dp)
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
            Button(onClick = {
                showDialog = true
            }) {
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
            Column {
                if (mealIngredients.isEmpty()) {
                    Text(text = stringResource(id = R.string.empty_ingredient_list))
                } else {
                    // Convert the entire list to Ingredient objects
                    val ingredients = mealIngredients.map { it.toIngredient() }
                    ingredients.forEach {
                        IngredientCard(
                            ingredient = it,
                            onEditButtonClicked = { ingredient ->
                                showDialog = true
                                ingredientToAdd = ingredient
                                Log.d("IngredientToAdd", ingredientToAdd.toString())
                                /*TODO("Implement the edit functionality so that the ingredient dialog have information about the ingredient we are editing")*/
                            },
                            onDeleteButtonClicked = { ingredient ->
                                mealIngredients.remove(ingredient.toIngredientItem())
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // conformation and cancellation buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        CoroutineScope(Dispatchers.IO).launch {

                            /*TODO("FIX THIS")*/

                            // Insert Meal and get ID (asynchronously)
                            val mealIdDeferred = async { databaseViewModel.insertMeal(Meal(mealName = mealName)) }

                            // Insert Ingredients and get IDs (asynchronously)
                            val ingredientIdDeferreds = mealIngredients.map { ingredientItem ->
                                async { databaseViewModel.insertIngredient(ingredientItem) }
                            }

                            // Wait for Meal ID
                            val mealId = mealIdDeferred.await()
                            Log.d("MealID", mealId.toString())

                            // Wait for IngredientIDs
                            val ingredientIds = ingredientIdDeferreds.map { it.await() }
                            Log.d("IngredientsID", ingredientIds.toString())

                            // Insert MealIngredients
                            ingredientIds.forEach { ingredientId ->
                                databaseViewModel.insertIngredientsForMeal(
                                    MealIngredients(
                                        mealID = mealId.toInt(),
                                        ingredientID = ingredientId.toInt()
                                    )
                                )
                            }
                        }
                    },
                    enabled = mealIngredients.isNotEmpty() && mealName.isNotBlank()
                ) {
                    Row {
                        Image(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.add),
                            modifier = Modifier.align(Alignment.CenterVertically)
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
            },
            onIngredientAdded = { newIngredient ->
                mealIngredients.add(newIngredient)
                totalCalorie = (totalCalorie.toInt() + newIngredient.calories.toInt()).toString()
                totalProtein = (totalProtein.toInt() + newIngredient.protein.toInt()).toString()
                totalCarbs = (totalCarbs.toInt() + newIngredient.carbs.toInt()).toString()
                totalFat = (totalFat.toInt() + newIngredient.fat.toInt()).toString()
            },
            ingredient = ingredientToAdd,
            databaseViewModel = databaseViewModel
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
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
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

//@Preview(showBackground = true)
//@Composable
//fun IngredientInfoPreview() {
//    IngredientInfo(title = "Calorie", value = "200", unit = "kcal")
//}

//@Preview(showBackground = true)
//@Composable
//fun IngredientCardPreview() {
//    IngredientCard(ingredient = Ingredient(
//        ingredientID = 1,
//        name = "Milk",
//        quantity = "2.5 oz",
//        calories = "220",
//        protein = "32",
//        carbs = "40",
//        fat = "12"
//    ))
//}

//@Preview(showSystemUi = true)
//@Composable
//fun MealAddScreenPreview() {
//    MealAddingScreen(
//        onCancelButtonClicked = {},
//        onBackButtonClicked = {},
//        databaseViewModel = hiltViewModel<DatabaseViewModel>()
//    )
//}