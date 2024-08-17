package com.example.calorietracker.datastore.repo

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.calorietracker.datastore.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepoImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepo {
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

    // using flow to observe changes
    override suspend fun getCompletedCalorie(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.COMPLETED_CALORIE].toString()
            }
    }


    override suspend fun updateCompletedCalorie(completedCalorie: Int) {
        dataStore.edit {
            it[PreferencesKeys.COMPLETED_CALORIE] = completedCalorie
        }
    }

    // using flow to observe changes
    override suspend fun getCompletedProtein(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.COMPLETED_PROTEIN].toString()
            }
    }

    override suspend fun updateCompletedProtein(completedProtein: Int) {
        dataStore.edit {
            it[PreferencesKeys.COMPLETED_PROTEIN] = completedProtein
        }
    }

    // using flow to observe changes
    override suspend fun getCompletedCarbs(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.COMPLETED_CARBS].toString()
            }
    }

    override suspend fun updateCompletedCarbs(completedCarbs: Int) {
        dataStore.edit {
            it[PreferencesKeys.COMPLETED_CARBS] = completedCarbs
        }
    }

    // using flow to observe changes
    override suspend fun getCompletedFat(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[PreferencesKeys.COMPLETED_FAT].toString()
            }
    }

    override suspend fun updateCompletedFat(completedFat: Int) {
        dataStore.edit {
            it[PreferencesKeys.COMPLETED_FAT] = completedFat
        }
    }
}