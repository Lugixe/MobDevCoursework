package ru.mobile.mobdevweatherforecast.domain.model

data class CityWeather(
    val cityName: String,
    val forecasts: List<DailyForecast>
)
