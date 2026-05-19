package ru.mobile.mobdevweatherforecast.ui.city

import ru.mobile.mobdevweatherforecast.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mobile.mobdevweatherforecast.databinding.FragmentCitySelectionBinding
import kotlinx.coroutines.launch
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController

class CitySelectionFragment : Fragment() {

    private var _binding: FragmentCitySelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CitySelectionViewModel by viewModels()
    private lateinit var adapter: CityAdapter //val ?? : private lateinit val adapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.availableCities.isEmpty()) {
            viewModel.availableCities = resources.getStringArray(ru.mobile.mobdevweatherforecast.R.array.default_cities).toList()
        }

        setupRecyclerView()
        observeViewModel()

        binding.fabShowWeather.setOnClickListener {
            val selectedUI = viewModel.selectedCities.value.toList()
            val selectedAPI = mutableListOf<String>()

            val allApiCities = resources.getStringArray(ru.mobile.mobdevweatherforecast.R.array.default_cities_api).toList()

            for (city in selectedUI) {
                val index = viewModel.availableCities.indexOf(city)
                if (index != -1) {
                    selectedAPI.add(allApiCities[index])
                }
            }

            val bundle = bundleOf(
                "cities_ui" to selectedUI.toTypedArray(),
                "cities_api" to selectedAPI.toTypedArray()
            )
            findNavController().navigate(ru.mobile.mobdevweatherforecast.R.id.action_citySelection_to_weather, bundle)
        }
    }

    private fun setupRecyclerView() {
        adapter = CityAdapter(viewModel.availableCities) { city, isChecked ->
            viewModel.toggleCity(city, isChecked)
        }
        binding.rvCities.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCities.adapter = adapter
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedCities.collect { selectedCities ->
                adapter.updateSelectedCities(selectedCities)


                if (selectedCities.isNotEmpty()) {
                    binding.fabShowWeather.show()
                } else {
                    binding.fabShowWeather.hide()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
