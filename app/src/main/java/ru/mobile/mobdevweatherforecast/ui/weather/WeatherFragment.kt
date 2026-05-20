package ru.mobile.mobdevweatherforecast.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.mobile.mobdevweatherforecast.databinding.FragmentWeatherBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModels { WeatherViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val uiCities = arguments?.getStringArray("cities_ui")?.toList() ?: emptyList()
        val apiCities = arguments?.getStringArray("cities_api")?.toList() ?: emptyList()

        val citiesMap = apiCities.zip(uiCities).toMap()

        if (viewModel.uiState.value is WeatherUiState.Loading && citiesMap.isNotEmpty()) {
            viewModel.loadWeatherForCities(citiesMap)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is WeatherUiState.Loading -> showLoading()
                    is WeatherUiState.Success -> showData(state)
                    is WeatherUiState.Error -> showError(state.message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.viewPagerWeather.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.tabLayout.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.viewPagerWeather.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.GONE
        binding.tvError.text = message
    }

    private fun showData(state: WeatherUiState.Success) {
        binding.progressBar.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.viewPagerWeather.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE

        val adapter = WeatherPagerAdapter(state.weatherList)
        binding.viewPagerWeather.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerWeather) { tab, position ->
            tab.text = state.weatherList[position].cityName
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
