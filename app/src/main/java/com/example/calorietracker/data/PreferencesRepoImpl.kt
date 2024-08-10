package com.example.calorietracker.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PreferencesRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): PreferencesRepo {
    override suspend fun getName(): String {
        return dataStore.data.first()[PreferencesKeys.NAME] ?: ""
    }

    override suspend fun updateName(name: String) {
        dataStore.edit {
            it[PreferencesKeys.NAME] = name
        }
    }

    override suspend fun getAge(): String {
        return dataStore.data.first()[PreferencesKeys.AGE].toString()
    }

    override suspend fun updateAge(age: Int) {
        dataStore.edit {
            it[PreferencesKeys.AGE] = age
        }
    }

    override suspend fun getGender(): String {
        return dataStore.data.first()[PreferencesKeys.GENDER] ?: "Male"
    }

    override suspend fun updateGender(gender: String) {
        dataStore.edit {
            it[PreferencesKeys.GENDER] = gender
        }
    }

    override suspend fun getWeight(): String {
        return dataStore.data.first()[PreferencesKeys.WEIGHT].toString()
    }

    override suspend fun updateWeight(weight: Double) {
        dataStore.edit {
            it[PreferencesKeys.WEIGHT] = weight
        }
    }

    override suspend fun getHeight(): String {
        return dataStore.data.first()[PreferencesKeys.HEIGHT].toString()
    }

    override suspend fun updateHeight(height: Double) {
        dataStore.edit {
            it[PreferencesKeys.HEIGHT] = height
        }
    }

    override suspend fun getPAL(): String {
        return dataStore.data.first()[PreferencesKeys.PAL].toString()
    }

    override suspend fun updatePAL(pal: Double) {
        dataStore.edit {
            it[PreferencesKeys.PAL] = pal
        }
    }

    override suspend fun getWeightGoal(): String {
        return dataStore.data.first()[PreferencesKeys.WEIGHT_GOAL] ?: "Maintain Weight"
    }

    override suspend fun updateWeightGoal(weightGoal: String) {
        dataStore.edit {
            it[PreferencesKeys.WEIGHT_GOAL] = weightGoal
        }
    }

    override suspend fun getCalorie(): String {
        return dataStore.data.first()[PreferencesKeys.CALORIE].toString()
    }

    override suspend fun updateCalorie(calorie: Int) {
        dataStore.edit {
            it[PreferencesKeys.CALORIE] = calorie
        }
    }

    override suspend fun getProtein(): String {
        return dataStore.data.first()[PreferencesKeys.PROTEIN].toString()
    }

    override suspend fun updateProtein(protein: Int) {
        dataStore.edit {
            it[PreferencesKeys.PROTEIN] = protein
        }
    }

    override suspend fun getCarbs(): String {
        return dataStore.data.first()[PreferencesKeys.CARBS].toString()
    }

    override suspend fun updateCarbs(carbs: Int) {
        dataStore.edit {
            it[PreferencesKeys.CARBS] = carbs
        }
    }

    override suspend fun getFat(): String {
        return dataStore.data.first()[PreferencesKeys.FAT].toString()
    }

    override suspend fun updateFat(fat: Int) {
        dataStore.edit {
            it[PreferencesKeys.FAT] = fat
        }
    }

    override suspend fun shouldShowOnboardingScreen(): Boolean {
        return dataStore.data.first()[PreferencesKeys.SHOW_ONBOARDING_SCREEN] ?: true
    }

    override suspend fun updateShouldShowOnboardingScreen(shouldShowOnboardingScreen: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.SHOW_ONBOARDING_SCREEN] = shouldShowOnboardingScreen
        }
    }
}