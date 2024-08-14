package com.example.calorietracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calorietracker.R

@Composable
fun IngredientAddingScreen(
    onCancelButtonClicked: () -> Unit = {}
) {
    // state variable for ingredient name
    var ingredientName by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for ingredient quantity
    var ingredientQuantity by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for ingredient calorie
    var ingredientCalorie by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for ingredient protein
    var ingredientProtein by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for ingredient carbs
    var ingredientCarbs by rememberSaveable {
        mutableStateOf("")
    }

    // state variable for ingredient fat
    var ingredientFat by rememberSaveable {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Row {
                TextButton(onClick = { onCancelButtonClicked() }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Spacer(modifier = Modifier.width(16.dp))
                TextButton(
                    onClick = { /*TODO*/ },
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
                    painter = painterResource(id = R.drawable.breakfast),
                    contentDescription = stringResource(id = R.string.add_ingredient),
                    modifier = Modifier.size(28.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.breakfast),
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
                    onValueChange = {
                        ingredientName = it
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
                // text field for ingredient quantity
                OutlinedTextField(
                    value = ingredientQuantity,
                    onValueChange = { value ->
                        ingredientQuantity = value.filter {
                            it.isDigit() || it == 'g' || it == 'o' || it == 'z' || it == ' ' || it == 'k'
                        }
                    },
                    label = {
                        Text(text = stringResource(id = R.string.ingredient_quantity))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

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

@Preview
@Composable
fun IngredientAddingScreenPreview() {
    IngredientAddingScreen()
}