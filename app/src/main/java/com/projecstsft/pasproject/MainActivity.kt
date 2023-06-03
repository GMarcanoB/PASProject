package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_login)

        /*Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition{true}*/
        /*val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()*/


    }
}