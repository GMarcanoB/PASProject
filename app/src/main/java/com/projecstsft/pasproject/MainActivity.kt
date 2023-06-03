package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var FirebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSplash = installSplashScreen()

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //setContentView(R.layout.activity_login)
        val loginBtn: Button = findViewById(R.id.loginButton)
        val email : EditText = findViewById(R.id.editEmailText)
        val password: EditText = findViewById(R.id.editPassword)

        loginBtn.setOnClickListener(){

        }
        /*Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition{true}*/
        /*val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()*/
    }
    private fun signIng(email:String, password: String){
        FirebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                if(task.isSuccessful){
                    val user = FirebaseAuth.currentUser
                    setContentView(R.layout.activity_main)
                }
            }
    }
}