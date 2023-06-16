package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projecstsft.pasproject.databinding.ActivityMainBinding
import setting.SettingActivity
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.BtnSetting.setOnClickListener { navToSetting() }

        super.onCreate(savedInstanceState)
    }

    private fun navToSetting() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Lógica para el ítem de ajustes
                navToSetting()
                true
            }
            R.id.action_logout -> {
                // Lógica para el ítem de logout
                performLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun performLogout() {
        // Implementa aquí la lógica para cerrar sesión
        // Por ejemplo, borra los datos de sesión, finaliza la actividad actual, etc.
    }
}