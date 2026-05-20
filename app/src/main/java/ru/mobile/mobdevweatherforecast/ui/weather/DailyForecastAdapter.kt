package ru.mobile.mobdevweatherforecast.ui.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mobile.mobdevweatherforecast.databinding.ItemForecastDayBinding
import ru.mobile.mobdevweatherforecast.domain.model.DailyForecast

class DailyForecastAdapter(private val items: List<DailyForecast>) :
    RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ItemForecastDayBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: DailyForecast) {
            binding.tvDate.text = item.date.substringAfter("-")

            binding.tvDescription.text = binding.root.context.getString(item.weatherDescResId)

            binding.tvTemp.text = "${item.maxTemp}° / ${item.minTemp}°"
        }
    }
}
