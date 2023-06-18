package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import setting.SettingActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.projecstsft.pasproject.databinding.ActivityMainBinding
import com.projecstsft.pasproject.databinding.FragmentWelcomeBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var welcomeBinding: FragmentWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val fanalytics = FirebaseAnalytics.getInstance(this)
        val bundlea = Bundle()
        bundlea.putString("message","Conexión con Analytics completa")
        fanalytics.logEvent("initScreen",bundlea)

        welcomeBinding = FragmentWelcomeBinding.inflate(layoutInflater)
        val bundle = intent.extras
        val email = bundle?.getString("mail")
        welcomeFragment(email?: "")
        title = "Home"
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
        FirebaseAuth.getInstance().signOut()
        onBackPressed()

    }

    private fun welcomeFragment(email:String){
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<WelcomeFragment>(R.id.welcome)
            welcomeBinding.textHello.text = email
        }
    }
}