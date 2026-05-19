package ru.mobile.mobdevweatherforecast.domain.repository

import ru.mobile.mobdevweatherforecast.domain.model.CityWeather

interface WeatherRepository {

    suspend fun getWeatherForCities(citiesMap: Map<String, String>): Result<List<CityWeather>>
}
