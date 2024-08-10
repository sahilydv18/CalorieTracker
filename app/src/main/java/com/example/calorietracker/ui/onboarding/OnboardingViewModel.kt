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
    private val _shouldShowOnboardingScreen = MutableStateFlow(true)
    val shouldShowOnboardingScreen = _shouldShowOnboardingScreen.asStateFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            _shouldShowOnboardingScreen.value = preferencesRepo.shouldShowOnboardingScreen()
        }
    }

    fun updateShouldShowOnboardingScreen(shouldShowOnboardingScreen: Boolean) {
        viewModelScope.launch {
            preferencesRepo.updateShouldShowOnboardingScreen(shouldShowOnboardingScreen)
        }
    }
}