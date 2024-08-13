package com.example.calorietracker.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calorietracker.R
import com.example.calorietracker.database.Ingredient

// screen for adding a meal
@Composable
fun MealAddScreen() {

    // state variable for meal name
    var mealName by rememberSaveable {
        mutableStateOf("")
    }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(top = 16.dp),
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
        Spacer(modifier = Modifier.height(16.dp))
        // button to add ingredients to a meal
        Button(onClick = { /*TODO*/ }) {
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
            item {
                IngredientCard(ingredient = Ingredient(
                    ingredientID = 1,
                    name = "Milk",
                    quantity = "2.5 oz",
                    calories = "220",
                    protein = "32",
                    carbs = "40",
                    fat = "12"
                ))
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
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.cancel))
            }
            Modifier.weight(1f)
            Button(onClick = { /*TODO*/ }) {
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

// card for displaying information about an ingredient
@Composable
private fun IngredientCard(ingredient: Ingredient) {
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
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit)
                        )
                    }
                    // delete icon button to delete an existing ingredient
                    IconButton(onClick = { /*TODO*/ }) {
                        Image(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(id = R.string.delete)
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Row {
                Column {
                    IngredientInfo(title = R.string.calorie_ingredient, value = ingredient.calories, unit = R.string.calorie_unit)
                    Spacer(modifier = Modifier.height(8.dp))
                    IngredientInfo(title = R.string.carbs_ingredient, value = ingredient.carbs, unit = R.string.gram_unit)
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    IngredientInfo(title = R.string.protein_ingredient, value = ingredient.protein, unit = R.string.gram_unit)
                    Spacer(modifier = Modifier.height(8.dp))
                    IngredientInfo(title = R.string.fat_ingredient, value = ingredient.fat, unit = R.string.gram_unit)
                }
            }
        }
    }
}

@Composable
private fun IngredientInfo(@StringRes title: Int, value: String, @StringRes unit: Int) {
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

@Preview(showSystemUi = true)
@Composable
fun MealAddScreenPreview() {
    MealAddScreen()
}