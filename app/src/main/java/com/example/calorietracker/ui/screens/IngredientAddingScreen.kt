package com.example.calorietracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.calorietracker.R
import com.example.calorietracker.database.Ingredient
import com.example.calorietracker.ui.viewmodel.IngredientItem

@Composable
fun IngredientAddingScreen(
    dismissDialog: () -> Unit,
    onIngredientAdded: (IngredientItem) -> Unit,
    ingredient: Ingredient
) {
    // state variable for ingredient name
    var ingredientName by rememberSaveable {
        mutableStateOf(ingredient.name)
    }

    // state variable for ingredient quantity
    var ingredientQuantity by rememberSaveable {
        mutableStateOf(getQuantity(ingredient.quantity))
    }

    // state variable for ingredient calorie
    var ingredientCalorie by rememberSaveable {
        mutableStateOf(ingredient.calories)
    }

    // state variable for ingredient protein
    var ingredientProtein by rememberSaveable {
        mutableStateOf(ingredient.protein)
    }

    // state variable for ingredient carbs
    var ingredientCarbs by rememberSaveable {
        mutableStateOf(ingredient.carbs)
    }

    // state variable for ingredient fat
    var ingredientFat by rememberSaveable {
        mutableStateOf(ingredient.fat)
    }

    // quantity type list
    val quantityType = LocalContext.current.resources.getStringArray(R.array.quantity_type_list)

    // state variable for selected quantity type
    var selectedQuantityType by rememberSaveable {
        if (getUnit(ingredient.quantity).isBlank()) {       // checking if the user is adding ingredient or editing ingredient, if he's adding then unit will be blank
            mutableStateOf(quantityType[0])                 // thus setting unit to first value in the list, else if he's editing then unit will not be blank and it
        } else {                                            // will be set to the unit of editing ingredient
            mutableStateOf(getUnit(ingredient.quantity))
        }
    }

    // state variable for showing dropdown menu
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Row {
                // cancel button
                TextButton(onClick = { dismissDialog() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Spacer(modifier = Modifier.width(16.dp))
                // add button
                TextButton(
                    onClick = {
                        val ingredientToAdd = IngredientItem(
                            name = ingredientName,
                            calories = ingredientCalorie,
                            quantity = "$ingredientQuantity $selectedQuantityType",
                            protein = ingredientProtein,
                            carbs = ingredientCarbs,
                            fat = ingredientFat
                        )
                        onIngredientAdded(ingredientToAdd)
                        dismissDialog()
                    },
                    enabled = ingredientName.isNotBlank() && ingredientQuantity.isNotBlank() && ingredientCalorie.isNotBlank() && ingredientProtein.isNotBlank() && ingredientCarbs.isNotBlank() && ingredientFat.isNotBlank()
                ) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        },
        title = {
            Text(text = stringResource(id = R.string.add_ingredient))
        },
        icon = {
            if (isSystemInDarkTheme()) {
                Image(
                    painter = painterResource(id = R.drawable.ingredient),
                    contentDescription = stringResource(id = R.string.add_ingredient),
                    modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ingredient),
                    contentDescription = stringResource(id = R.string.add_ingredient),
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        text = {
            Column {
                Text(text = stringResource(id = R.string.ingredient_info))
                Spacer(modifier = Modifier.height(16.dp))
                // text field for ingredient name
                OutlinedTextField(
                    value = ingredientName,
                    onValueChange = { value ->
                        ingredientName = value.filter {
                            it.isLetter() || it == ' '
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.ingredient_name))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // text field for ingredient quantity
                    OutlinedTextField(
                        value = ingredientQuantity,
                        onValueChange = { value ->
                            ingredientQuantity = value.filter {     // making sure that the user can only enter valid integer numbers or decimal numbers up to 2 places and not any whitespaces
                                it.isDigit() || it == '.'
                            }.replace(
                                Regex("(\\.[0-9]{2}).*"), "$1"
                            )
                        },
                        label = {
                            Text(text = stringResource(id = R.string.ingredient_quantity))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.width(140.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // Dropdown menu for selecting quantity type
                    Box(
                        modifier = Modifier.align(Alignment.Bottom)
                    ) {
                        OutlinedTextField(
                            value = selectedQuantityType,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.width(120.dp),
                            label = {
                                Text(text = stringResource(id = R.string.quantity_type))
                            },
                            trailingIcon = {
                                IconButton(onClick = { expanded = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = ""
                                    )
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            scrollState = rememberScrollState()
                        ) {
                            quantityType.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = it)
                                    },
                                    onClick = {
                                        selectedQuantityType = it
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                // text field for ingredient calorie
                OutlinedTextField(
                    value = ingredientCalorie,
                    onValueChange = { value ->
                        ingredientCalorie = value.filter {
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.calorie))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                // text field for ingredient protein
                OutlinedTextField(
                    value = ingredientProtein,
                    onValueChange = { value ->
                        ingredientProtein = value.filter {
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.protein))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                // text field for ingredient carbs
                OutlinedTextField(
                    value = ingredientCarbs,
                    onValueChange = { value ->
                        ingredientCarbs = value.filter {
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.carbs))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                // text field for ingredient fat
                OutlinedTextField(
                    value = ingredientFat,
                    onValueChange = { value ->
                        ingredientFat = value.filter {
                            it.isDigit()
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.fat))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
    )
}

// function to remove unit from the quantity so that we can display the accurate quantity while editing the ingredient
private fun getQuantity(quantityWithUnit: String): String {
    return quantityWithUnit.substringBefore(" ")
}

// function to get unit from the quantity so that we can display the accurate quantity unit while editing the ingredient
private fun getUnit(quantityWithUnit: String): String {
    return quantityWithUnit.substringAfter(" ")
}