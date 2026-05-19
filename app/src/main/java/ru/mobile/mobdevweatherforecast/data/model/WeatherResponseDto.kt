package ru.mobile.mobdevweatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("daily") val daily: DailyWeatherDto
)
