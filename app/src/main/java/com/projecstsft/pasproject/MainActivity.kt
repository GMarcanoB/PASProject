package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projecstsft.pasproject.databinding.ActivityMainBinding
import setting.SettingActivity

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
}