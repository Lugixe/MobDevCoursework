package ru.mobile.mobdevweatherforecast.data.mapper

import ru.mobile.mobdevweatherforecast.R

object WeatherMapper {
    fun mapWeatherCodeToResId(code: Int): Int {
        return when (code) {
            0 -> R.string.weather_clear
            1, 2, 3 -> R.string.weather_cloudy
            45, 48 -> R.string.weather_fog
            51, 53, 55, 56, 57 -> R.string.weather_drizzle
            61, 63, 65, 66, 67 -> R.string.weather_rain
            71, 73, 75, 77 -> R.string.weather_snow
            80, 81, 82 -> R.string.weather_shower
            95, 96, 99 -> R.string.weather_storm
            else -> R.string.weather_unknown
        }
    }
}