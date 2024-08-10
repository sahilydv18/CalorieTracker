package com.example.calorietracker.data

// all functions to get and set values in data store preferences file
interface PreferencesRepo {
    suspend fun getName(): String
    suspend fun updateName(name: String)

    suspend fun getAge(): String
    suspend fun updateAge(age: Int)

    suspend fun getGender(): String
    suspend fun updateGender(gender: String)

    suspend fun getWeight(): String
    suspend fun updateWeight(weight: Double)

    suspend fun getHeight(): String
    suspend fun updateHeight(height: Double)

    suspend fun getPAL(): String
    suspend fun updatePAL(pal: Double)

    suspend fun getWeightGoal(): String
    suspend fun updateWeightGoal(weightGoal: String)

    suspend fun getCalorie(): String
    suspend fun updateCalorie(calorie: Int)

    suspend fun getProtein(): String
    suspend fun updateProtein(protein: Int)

    suspend fun getCarbs(): String
    suspend fun updateCarbs(carbs: Int)

    suspend fun getFat(): String
    suspend fun updateFat(fat: Int)

    suspend fun shouldShowOnboardingScreen(): Boolean
    suspend fun updateShouldShowOnboardingScreen(shouldShowOnboardingScreen: Boolean)
}