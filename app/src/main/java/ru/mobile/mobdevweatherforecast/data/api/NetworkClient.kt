package ru.mobile.mobdevweatherforecast.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val ninjasRetrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val meteoRetrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val ninjasService: ApiNinjasService = ninjasRetrofit.create(ApiNinjasService::class.java)
    val meteoService: OpenMeteoService = meteoRetrofit.create(OpenMeteoService::class.java)
}