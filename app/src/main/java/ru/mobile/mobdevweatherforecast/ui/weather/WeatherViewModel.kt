package ru.mobile.mobdevweatherforecast.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ru.mobile.mobdevweatherforecast.data.api.NetworkClient
import ru.mobile.mobdevweatherforecast.data.repository.WeatherRepositoryImpl
import ru.mobile.mobdevweatherforecast.domain.model.CityWeather
import ru.mobile.mobdevweatherforecast.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Состояния нашего экрана (Загрузка, Успех, Ошибка)
sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val weatherList: List<CityWeather>) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun loadWeatherForCities(citiesMap: Map<String, String>) {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            val result = repository.getWeatherForCities(citiesMap)
            result.onSuccess { weatherData ->
                if (weatherData.isEmpty()) {
                    _uiState.value = WeatherUiState.Error("Не удалось найти данные для выбранных городов")
                } else {
                    _uiState.value = WeatherUiState.Success(weatherData)
                }
            }.onFailure { error ->
                _uiState.value = WeatherUiState.Error(error.message ?: "Произошла неизвестная ошибка")
            }
        }
    }
}

// Фабрика нужна, потому что во ViewModel мы передаем параметр (repository)
class WeatherViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            val repository = WeatherRepositoryImpl(
                ninjasService = NetworkClient.ninjasService,
                meteoService = NetworkClient.meteoService
            )
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
