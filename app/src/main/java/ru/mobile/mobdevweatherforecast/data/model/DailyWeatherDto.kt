package ru.mobile.mobdevweatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class DailyWeatherDto(
    @SerializedName("time") val time: List<String>,
    @SerializedName("temperature_2m_max") val maxTemp: List<Double>,
    @SerializedName("temperature_2m_min") val minTemp: List<Double>,
    @SerializedName("weathercode") val weatherCode: List<Int>
)
