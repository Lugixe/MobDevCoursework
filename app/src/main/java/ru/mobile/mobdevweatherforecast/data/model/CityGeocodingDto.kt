package ru.mobile.mobdevweatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class CityGeocodingDto(
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
