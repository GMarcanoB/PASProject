package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import setting.SettingActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import com.projecstsft.pasproject.databinding.ActivityMainBinding

enum class ProviderType{
    BASIC
}
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

       /* supportFragmentManager.commit {
            setReorderingAllowed(true)
          //  add()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Lógica para el ítem de ajustes
                startActivity(Intent(this, SettingActivity::class.java))
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