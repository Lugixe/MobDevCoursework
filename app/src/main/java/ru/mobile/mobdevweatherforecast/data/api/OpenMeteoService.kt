package ru.mobile.mobdevweatherforecast.data.api

import ru.mobile.mobdevweatherforecast.data.model.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("daily") daily: String = "temperature_2m_max,temperature_2m_min,weathercode",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") days: Int = 7
    ): WeatherResponseDto
}
