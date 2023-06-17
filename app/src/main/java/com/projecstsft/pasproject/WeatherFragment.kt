package com.projecstsft.pasproject

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
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
                Log.i("response", response.toString())
                binding.weatherImg.text = weatherEmoji
                binding.weatherText.text = weatherData.weather[0].main

                binding.temperature.text = formatTemperature(weatherData.main.temp)
                binding.day.text = getDayFromTimestamp(weatherData.dt)
                binding.city.text = weatherData.name

                updateWeatherCardBackground(weatherData.weather[0].main, isDayTime())
            },
            { error -> error.printStackTrace() })

        requestQueue.add(jsonObjectRequest)
    }

    private fun updateWeatherCardBackground(weather: String, isDayTime: Boolean) {
        val startColor: Int
        val endColor: Int

        when (weather.lowercase()) {
            "clear" -> {
                if (isDayTime) {
                    startColor = Color.parseColor("#FF6B00") // Color naranja
                    endColor = Color.parseColor("#FFD600") // Color amarillo
                } else {
                    startColor = Color.parseColor("#192A49") // Color azul oscuro
                    endColor = Color.parseColor("#000000") // Color negro
                }
            }
            "clouds" -> {
                startColor = Color.parseColor("#6088A0") // Color azul claro
                endColor = Color.parseColor("#FFFFFF") // Color blanco
            }
            "rain" -> {
                startColor = Color.parseColor("#4D71A3") // Color azul
                endColor = Color.parseColor("#90A4BF") // Color azul claro
            }
            "snow" -> {
                startColor = Color.parseColor("#C7D8F3") // Color azul claro
                endColor = Color.parseColor("#FFFFFF") // Color blanco
            }
            "thunderstorm" -> {
                startColor = Color.parseColor("#293955") // Color azul oscuro
                endColor = Color.parseColor("#677C95") // Color azul grisáceo
            }
            "drizzle" -> {
                startColor = Color.parseColor("#9CAFBF") // Color azul verdoso
                endColor = Color.parseColor("#D1E1E9") // Color azul claro
            }
            else -> {
                startColor = Color.parseColor("#000000") // Color negro
                endColor = Color.parseColor("#000000") // Color negro
            }
        }

        val gradient = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, intArrayOf(startColor, endColor))
        gradient.gradientRadius = 400f
        gradient.gradientType = GradientDrawable.LINEAR_GRADIENT
        gradient.cornerRadius = resources.getDimensionPixelSize(R.dimen.card_corner_radius).toFloat()

        binding.WeatherCard.apply {
            background = gradient
            invalidate() // Actualizar el fondo de la tarjeta
        }
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

    private fun formatTemperature(temperature: Double): String {
        // Formato: 25.6 °C
        val formattedTemperature = String.format("%.1f", temperature - 273.15)
        return "$formattedTemperature °C"
    }

    private fun getDayFromTimestamp(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000 // Convertir segundos a milisegundos

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val days = arrayOf("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")

        return days[dayOfWeek - 1] // Restar 1 para que el índice coincida con el día de la semana
    }

    companion object {
        @JvmStatic fun newInstance() = WeatherFragment()
    }
}

data class WeatherData(
    val weather: List<Weather>,
    val name: String,
    val main: Main,
    val dt: Long
)

data class Main(
    val temp: Double
)
data class Weather(
    val main: String
)