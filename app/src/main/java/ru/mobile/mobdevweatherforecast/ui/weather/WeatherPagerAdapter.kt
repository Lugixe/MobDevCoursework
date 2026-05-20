package ru.mobile.mobdevweatherforecast.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mobile.mobdevweatherforecast.databinding.ItemWeatherPageBinding
import ru.mobile.mobdevweatherforecast.domain.model.CityWeather

class WeatherPagerAdapter(private val citiesWeather: List<CityWeather>) :
    RecyclerView.Adapter<WeatherPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(citiesWeather[position])
    }

    override fun getItemCount() = citiesWeather.size

    inner class ViewHolder(private val binding: ItemWeatherPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cityWeather: CityWeather) {
            binding.tvCityNameTitle.text = cityWeather.cityName


            val adapter = DailyForecastAdapter(cityWeather.forecasts)
            binding.rvDailyForecast.adapter = adapter
        }
    }
}
