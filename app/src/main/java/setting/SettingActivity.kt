package setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.projecstsft.pasproject.databinding.ActivitySettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import android.media.AudioManager
import android.view.Menu
import android.view.MenuItem
import com.projecstsft.pasproject.R

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class SettingActivity : AppCompatActivity() {

    companion object{
        const val VOLUME_LVL = "volume_lvl"
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_BLUETOOTH = "key_bluetooth"
    }

    private lateinit var binding: ActivitySettingBinding
    private var firstTime: Boolean = true
    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings)

        CoroutineScope(Dispatchers.IO).launch {
            getSetting().filter { firstTime }.collect{settingModel ->
                if(settingModel != null){
                    runOnUiThread {
                        binding.switchBluetooth.isChecked = settingModel.bluetooth
                        binding.switchDarkMode.isChecked = settingModel.darkMode
                        binding.rsVolume.setValues(settingModel.volume.toFloat())
                        firstTime = !firstTime
                    }
                }
            }
        }

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        initUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener { _, value, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
            setVolumeLevel(value.toInt())
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            if(value) enableDarkMode()
            else disableDarkMode()

            CoroutineScope(Dispatchers.IO).launch {
                saveOpt(KEY_DARK_MODE, value)
            }
        }

        binding.switchBluetooth.setOnCheckedChangeListener { _, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOpt(KEY_BLUETOOTH, value)
            }
        }
    }

    private suspend fun saveVolume(value: Int){
        dataStore.edit { it ->
            it[intPreferencesKey(VOLUME_LVL)] = value
        }
    }

    private fun setVolumeLevel(volume: Int) {
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val scaledVolume = (maxVolume * (volume.toFloat() / 100)).toInt()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, scaledVolume, 0)
    }

    private suspend fun saveOpt(key:String, value:Boolean){
        dataStore.edit {it ->
            it[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSetting(): Flow<SettingModel> {
        return dataStore.data.map { it ->
            SettingModel(
                volume = it[intPreferencesKey(VOLUME_LVL)] ?: 50,
                bluetooth = it[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                darkMode = it[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
            )
        }
    }

    private fun enableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode(){
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}