package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.projecstsft.pasproject.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        registerBinding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        backLogin()
    }

    fun createAccount(view: View) {}

    private fun backLogin(){
        registerBinding.textloginIn.setOnClickListener{
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }



}
