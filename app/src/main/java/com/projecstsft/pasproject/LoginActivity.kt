package com.projecstsft.pasproject

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        val loginBtn: Button = findViewById(R.id.loginButton)
        val email : EditText = findViewById(R.id.editEmailText)
        val password : EditText = findViewById(R.id.editPassword)
        val singup : TextView = findViewById(R.id.signup)

        loginBtn.setOnClickListener {
           when {
                password.text.toString().isEmpty() || email.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    logIn(email.text.toString(), password.text.toString())
                }
            }

        }

    }

    private fun logIn(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    //setContentView(R.layout.activity_main)
                   //Toast.makeText(baseContext, user?.uid.toString(), Toast.LENGTH_SHORT).show()
                    //val intnt = Intent(this, MainActivity::class.java)
                   // startActivity(intnt)
                    Toast.makeText(this,"Operación Exitosa!", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                }
                else{
                    Toast.makeText(baseContext, "Email y/o contraseña incorrectos.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {

    }
    private fun reload() {
        TODO("Not yet implemented")
    }

    fun clickTextView(view: View) {
        setContentView(R.layout.activity_register)
    }
}