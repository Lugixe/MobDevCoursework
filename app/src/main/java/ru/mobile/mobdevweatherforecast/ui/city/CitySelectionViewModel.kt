package ru.mobile.mobdevweatherforecast.ui.city

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CitySelectionViewModel : ViewModel() {

    var availableCities: List<String> = emptyList()

    private val _selectedCities = MutableStateFlow<Set<String>>(emptySet())
    val selectedCities: StateFlow<Set<String>> = _selectedCities.asStateFlow()

    fun toggleCity(city: String, isChecked: Boolean) {
        val currentSet = _selectedCities.value.toMutableSet()
        if (isChecked) {
            currentSet.add(city)
        } else {
            currentSet.remove(city)
        }
        _selectedCities.value = currentSet
    }
}
