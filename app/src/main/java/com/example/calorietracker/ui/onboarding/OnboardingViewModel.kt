package com.example.calorietracker.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.datastore.repo.PreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
): ViewModel() {

    // variable for getting value to show onboarding screen or not
    private val _shouldShowOnboardingScreen = MutableStateFlow(false)
    val shouldShowOnboardingScreen = _shouldShowOnboardingScreen.asStateFlow()

    // variable for getting the completed calorie value from data store, using flow to observe changes
    private val _completedCalorie = MutableStateFlow("")
    val completedCalorie: StateFlow<String> = _completedCalorie.asStateFlow()

    // variable for getting the completed protein value from data store, using flow to observe changes
    private val _completedProtein = MutableStateFlow("")
    val completedProtein: StateFlow<String> = _completedProtein.asStateFlow()

    // variable for getting the completed carbs value from data store, using flow to observe changes
    private val _completedCarbs = MutableStateFlow("")
    val completedCarbs: StateFlow<String> = _completedCarbs.asStateFlow()

    // variable for getting the completed fat value from data store, using flow to observe changes
    private val _completedFat = MutableStateFlow("")
    val completedFat: StateFlow<String> = _completedFat.asStateFlow()

    init {
        viewModelScope.launch {
            _shouldShowOnboardingScreen.value = preferencesRepo.shouldShowOnboardingScreen()
        }

        viewModelScope.launch {
            preferencesRepo.getCompletedCalorie().collect {
                _completedCalorie.value = it
            }
        }

        viewModelScope.launch {
            preferencesRepo.getCompletedProtein().collect {
                _completedProtein.value = it
            }
        }

        viewModelScope.launch {
            preferencesRepo.getCompletedCarbs().collect {
                _completedCarbs.value = it
            }
        }

        viewModelScope.launch {
            preferencesRepo.getCompletedFat().collect {
                _completedFat.value = it
            }
        }
    }

    fun updateShouldShowOnboardingScreen(shouldShowOnboardingScreen: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateShouldShowOnboardingScreen(shouldShowOnboardingScreen)
        }
    }

    // functions to get and update name
    suspend fun getName(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getName()
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateName(name)
        }
    }

    // functions to get and update age
    suspend fun getAge(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getAge()
        }
    }

    fun updateAge(age: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateAge(age)
        }
    }

    // functions to get and update gender
    suspend fun getGender(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getGender()
        }
    }

    fun updateGender(gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateGender(gender)
        }
    }

    // functions to get and update weight
    suspend fun getWeight(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getWeight()
        }
    }

    fun updateWeight(weight: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateWeight(weight)
        }
    }

    // functions to get and update height
    suspend fun getHeight(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getHeight()
        }
    }

    fun updateHeight(height: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateHeight(height)
        }
    }

    // functions to get and update pal
    suspend fun getPAL(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getPAL()
        }
    }

    fun updatePAL(pal: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updatePAL(pal)
        }
    }

    // functions to get and update weight goal
    suspend fun getWeightGoal(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getWeightGoal()
        }
    }

    fun updateWeightGoal(weightGoal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateWeightGoal(weightGoal)
        }
    }

    // functions to get and update calorie
    suspend fun getCalorie(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getCalorie()
        }
    }

    fun updateCalorie(calorie: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateCalorie(calorie)
        }
    }

    // functions to get and update protein
    suspend fun getProtein(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getProtein()
        }
    }

    fun updateProtein(protein: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateProtein(protein)
        }
    }

    // functions to get and update carbs
    suspend fun getCarbs(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getCarbs()
        }
    }

    fun updateCarbs(carbs: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateCarbs(carbs)
        }
    }

    // functions to get and update fat
    suspend fun getFat(): String {
        return withContext(Dispatchers.IO) {
            preferencesRepo.getFat()
        }
    }

    fun updateFat(fat: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateFat(fat)
        }
    }

    // functions to get completed calories
    fun updateCompletedCalorie(completedCalorie: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateCompletedCalorie(completedCalorie)
        }
    }

    // functions to get completed protein
    fun updateCompletedProtein(completedProtein: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateCompletedProtein(completedProtein)
        }
    }

    // functions to get completed carbs
    fun updateCompletedCarbs(completedCarbs: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateCompletedCarbs(completedCarbs)
        }
    }

    // functions to get and update completed fat
    fun updateCompletedFat(completedFat: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepo.updateCompletedFat(completedFat)
        }
    }
}