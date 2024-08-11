package com.example.calorietracker.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorietracker.data.PreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
): ViewModel() {

    // variable for getting value to show onboarding screen or not
    private val _shouldShowOnboardingScreen = MutableStateFlow(false)
    val shouldShowOnboardingScreen = _shouldShowOnboardingScreen.asStateFlow()

    // NOTE : this is a temporary solution, fix it when you use database to store meals
    // a list made to control the showing of splash screen, it will contain the value from data store preferences file
    val conditionForSplashScreen: MutableList<Boolean> = mutableListOf()

    init {
        viewModelScope.launch {
            _shouldShowOnboardingScreen.value = preferencesRepo.shouldShowOnboardingScreen()
            // NOTE : this is a temporary solution, fix it when you use database to store meals
            // adding the value from data store preferences about whether to show onboarding screen or not in the list to use it to control the splash screen
            conditionForSplashScreen.add(_shouldShowOnboardingScreen.value)
        }
    }

    fun updateShouldShowOnboardingScreen(shouldShowOnboardingScreen: Boolean) {
        viewModelScope.launch {
            preferencesRepo.updateShouldShowOnboardingScreen(shouldShowOnboardingScreen)
        }
    }
}