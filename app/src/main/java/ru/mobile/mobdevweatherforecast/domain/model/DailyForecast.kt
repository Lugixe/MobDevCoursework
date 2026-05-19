package ru.mobile.mobdevweatherforecast.domain.model

data class DailyForecast(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weatherDescResId: Int
)
