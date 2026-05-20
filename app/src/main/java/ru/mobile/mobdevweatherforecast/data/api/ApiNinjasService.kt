package ru.mobile.mobdevweatherforecast.data.api
import ru.mobile.mobdevweatherforecast.data.model.CityGeocodingDto

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiNinjasService {
    @GET("v1/city")
    suspend fun getCityCoordinates(
        @Query("name") cityName: String,
        @Header("X-Api-Key") apiKey: String = "LcpTwr0RDvifPQoLmT8BLIyczm8PMRqOOXZ53IMd"
    ): List<CityGeocodingDto>
}
