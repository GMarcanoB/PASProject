package com.projecstsft.pasproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.projecstsft.pasproject.databinding.FragmentWeatherBinding
import java.util.Calendar

class WeatherFragment : Fragment() {
    private var _binding:FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val apiKey = "0d8f484ceb801b67cd1e1c63ea5e2d52" // Reemplaza con tu API Key de OpenWeatherMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        // Consulta la API de clima
        val url = "https://api.openweathermap.org/data/2.5/weather?q=Madrid&appid=$apiKey"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val weatherData = Gson().fromJson(response.toString(), WeatherData::class.java)
                val weatherEmoji = getWeatherEmoji(weatherData.weather[0].main, isDayTime())

                binding.weatherTextView.text = weatherEmoji
                // temperatureTextView.text = "${weatherData.main.temp}°C"
            },
            { error -> error.printStackTrace() })

        requestQueue.add(jsonObjectRequest)
    }

    private fun getWeatherEmoji(weather: String, isDayTime: Boolean): String {
        return when (weather.lowercase()) {
            "clear" -> if (isDayTime) "☀️" else "🌙"
            "clouds" -> "☁️"
            "rain" -> "🌧️"
            "snow" -> "❄️"
            "thunderstorm" -> "⛈️"
            "drizzle" -> "🌦️"
            else -> "❓"
        }
    }

    private fun isDayTime(): Boolean {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        return currentHour in 6..18
    }

    companion object {
        @JvmStatic fun newInstance() = WeatherFragment()
    }
}

data class WeatherData(
    val weather: List<Weather>
)

data class Weather(
    val main: String
)