package com.projecstsft.pasproject

import android.graphics.Color
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
import kotlinx.coroutines.DelicateCoroutinesApi
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

    @OptIn(DelicateCoroutinesApi::class)
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
                    updateInfo(response.body?.string())
                }
            })
        }
    }

    private fun updateInfo(jsonData: String?) {
        //Log.i("jsonData", jsonData.toString())
        //jsonData: {"status":"success","data":{"city":"Moncloa-Aravaca","state":"Madrid","country":"Spain","location":{"type":"Point","coordinates":[-3.7317,40.43547]},"current":{"pollution":{"ts":"2023-06-17T21:00:00.000Z","aqius":34,"mainus":"p2","aqicn":12,"maincn":"p2"},"weather":{"ts":"2023-06-17T21:00:00.000Z","tp":26,"pr":1010,"hu":50,"ws":2.57,"wd":260,"ic":"01n"}}}}
        val airQualityIndex = parseAirQualityIndex(jsonData)
        updateAirQualityCardBackground(airQualityIndex)

        activity?.runOnUiThread {
            binding.emoji.text = getEmoji(airQualityIndex)
            binding.number.text = airQualityIndex.toString()
            binding.quality.text = getAirQualityTerm(airQualityIndex)
            binding.state.text = getState(jsonData)
            binding.city.text = getCity(jsonData)
        }
    }

    private fun getState(jsonData: String?): CharSequence? {
        return JSONObject(jsonData)
            .getJSONObject("data")
            .getString("state")
    }

    private fun getCity(jsonData: String?): String{
        return JSONObject(jsonData)
            .getJSONObject("data")
            .getString("city")
    }

    private fun parseAirQualityIndex(jsonData: String?): Int {
        return JSONObject(jsonData)
            .getJSONObject("data")
            .getJSONObject("current")
            .getJSONObject("pollution")
            .getInt("aqius")
    }

    private fun getEmoji(airQualityIndex: Int): String {
        return when {
            airQualityIndex <= 50 -> "😀" // Buena calidad del aire
            airQualityIndex <= 100 -> "😊" // Calidad del aire moderada
            airQualityIndex <= 150 -> "😷" // Calidad del aire no saludable para grupos sensibles
            else -> "🤢" // Calidad del aire no saludable
        }
    }

    private fun getAirQualityTerm(airQualityIndex: Int): String {
        return when {
            airQualityIndex <= 50 -> "Buena"
            airQualityIndex <= 100 -> "Moderada"
            airQualityIndex <= 150 -> "No saludable"
            else -> "No saludable"
        }
    }

    private fun updateAirQualityCardBackground(airQualityIndex: Int) {
        val cardView = binding.AirQualityCard

        val backgroundColor: Int = when {
            airQualityIndex <= 50 -> Color.parseColor("#a8e05f")
            airQualityIndex <= 100 -> Color.parseColor("#efbe1d")
            airQualityIndex <= 150 -> Color.parseColor("#f27e2f")
            else -> Color.BLACK
        }

        val cornerRadius = resources.getDimension(R.dimen.card_corner_radius)
        cardView.apply {
            setCardBackgroundColor(backgroundColor)
            radius = cornerRadius
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = AirQualityFragment()
    }
}
