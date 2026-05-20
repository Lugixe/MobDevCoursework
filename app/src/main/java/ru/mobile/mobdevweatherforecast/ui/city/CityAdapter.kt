package ru.mobile.mobdevweatherforecast.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mobile.mobdevweatherforecast.databinding.ItemCityBinding

class CityAdapter(
    private val cities: List<String>,
    private val onCityChecked: (String, Boolean) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var selectedCities: Set<String> = emptySet()

    fun updateSelectedCities(newSelected: Set<String>) {
        selectedCities = newSelected
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    override fun getItemCount() = cities.size

    inner class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: String) {
            binding.tvCityName.text = city

            binding.cbCity.setOnCheckedChangeListener(null)
            binding.cbCity.isChecked = selectedCities.contains(city)

            binding.cbCity.setOnCheckedChangeListener { _, isChecked ->
                onCityChecked(city, isChecked)
            }
        }
    }
}
