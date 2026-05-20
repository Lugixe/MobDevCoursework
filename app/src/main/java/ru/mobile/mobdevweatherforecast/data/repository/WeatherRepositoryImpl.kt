package ru.mobile.mobdevweatherforecast.data.repository

import ru.mobile.mobdevweatherforecast.data.api.ApiNinjasService
import ru.mobile.mobdevweatherforecast.data.api.OpenMeteoService
import ru.mobile.mobdevweatherforecast.data.mapper.WeatherMapper
import ru.mobile.mobdevweatherforecast.domain.model.CityWeather
import ru.mobile.mobdevweatherforecast.domain.model.DailyForecast
import ru.mobile.mobdevweatherforecast.domain.repository.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class WeatherRepositoryImpl(
    private val ninjasService: ApiNinjasService,
    private val meteoService: OpenMeteoService
) : WeatherRepository {

    override suspend fun getWeatherForCities(citiesMap: Map<String, String>): Result<List<CityWeather>> {
        return try {
            val results = coroutineScope {

                citiesMap.map { (apiName, uiName) ->
                    async { fetchCityWeather(apiName, uiName) }
                }.awaitAll().filterNotNull()
            }
            Result.success(results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun fetchCityWeather(apiName: String, uiName: String): CityWeather? {

        val geoResult = ninjasService.getCityCoordinates(apiName)
        if (geoResult.isEmpty()) return null
        val coords = geoResult.first()

        val weatherResult = meteoService.getWeather(lat = coords.latitude, lon = coords.longitude)

        val forecasts = mutableListOf<DailyForecast>()
        val daily = weatherResult.daily

        for (i in daily.time.indices) {
            forecasts.add(
                DailyForecast(
                    date = daily.time[i],
                    maxTemp = daily.maxTemp[i],
                    minTemp = daily.minTemp[i],
                    weatherDescResId = WeatherMapper.mapWeatherCodeToResId(daily.weatherCode[i])
                )
            )
        }

        return CityWeather(
            cityName = uiName,
            forecasts = forecasts
        )
    }

}
