package com.projecstsft.pasproject
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

import com.projecstsft.pasproject.databinding.ActivityMainBinding

import java.util.*

data class WeatherData(
    val weather: List<Weather>
)

data class Weather(
    val main: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val apiKey = "0d8f484ceb801b67cd1e1c63ea5e2d52" // API Key de OpenWeatherMap
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Consulta la API de clima
        val url = "https://api.openweathermap.org/data/2.5/weather?q=Madrid&appid=$apiKey"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val weatherData = Gson().fromJson(response.toString(), WeatherData::class.java)
                val weatherEmoji = getWeatherEmoji(weatherData.weather[0].main, isDayTime())
                mainBinding.weatherEmojiTextView.text = weatherEmoji
//                temperatureTextView.text = "${weatherData.main.temp}°C"
            },
            { error ->
                error.printStackTrace()
            })

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
}