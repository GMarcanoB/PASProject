package com.projecstsft.pasproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import android.util.Log
import com.projecstsft.pasproject.databinding.FragmentAirQualityBinding
import java.io.IOException

class AirQualityFragment : Fragment() {
    private var _binding:FragmentAirQualityBinding? = null
    private val binding get() = _binding!!

    private val apiKey = "d1542388-a4ef-4ed0-9021-ce78bc21a477" // API key de IQAir

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirQualityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAirQualityData()

    }

    private fun fetchAirQualityData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.airvisual.com/v2/nearest_city?key=$apiKey")
            .build()

        GlobalScope.launch(Dispatchers.Main) {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Manejar error de conexión
                    Log.e("AirQualityFragment", "Error de conexión: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body?.string()
                    val airQualityIndex = parseAirQualityIndex(jsonData)
                    updateEmoji(airQualityIndex)
                }
            })
        }
    }

    private fun parseAirQualityIndex(jsonData: String?): Int {
        return JSONObject(jsonData)
            .getJSONObject("data")
            .getJSONObject("current")
            .getJSONObject("pollution")
            .getInt("aqius")
    }

    private fun updateEmoji(airQualityIndex: Int) {
        val emoji: String = when {
            airQualityIndex <= 50 -> "😀" // Buena calidad del aire
            airQualityIndex <= 100 -> "😊" // Calidad del aire moderada
            airQualityIndex <= 150 -> "😷" // Calidad del aire no saludable para grupos sensibles
            else -> "🤢" // Calidad del aire no saludable
        }

        activity?.runOnUiThread {
            binding.emojiTextView.text = emoji
            binding.numberTextView.text = airQualityIndex.toString()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AirQualityFragment()
    }
}
