package com.projecstsft.pasproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.projecstsft.pasproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        auth = Firebase.auth

       val email = loginBinding.textEmail.text.toString()
       val password = loginBinding.textPassword.text.toString()


        loginBinding.loginButton.setOnClickListener {
           when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                }
                else -> {
                    logIn(email, password)
                }
            }

        }

        goRegister()

    }

    private fun logIn(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
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
    private fun goRegister(){
        loginBinding.signup.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}