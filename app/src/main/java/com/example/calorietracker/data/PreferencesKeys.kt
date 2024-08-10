package com.example.calorietracker.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

// all the keys used in the data store preference file to store data
class PreferencesKeys {
    companion object {
        val NAME = stringPreferencesKey("user_name")
        val AGE = intPreferencesKey("user_age")
        val GENDER = stringPreferencesKey("user_gender")
        val WEIGHT = doublePreferencesKey("user_weight")
        val HEIGHT = doublePreferencesKey("user_height")
        val PAL = doublePreferencesKey("user_pal")
        val WEIGHT_GOAL = stringPreferencesKey("goal_weight")
        val CALORIE = intPreferencesKey("calorie_intake")
        val PROTEIN = intPreferencesKey("protein_intake")
        val CARBS = intPreferencesKey("carbs_intake")
        val FAT = intPreferencesKey("fat_intake")
        val SHOW_ONBOARDING_SCREEN = booleanPreferencesKey("should_show_onboarding_screen")
    }
}