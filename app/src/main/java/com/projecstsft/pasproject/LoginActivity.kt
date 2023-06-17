package com.projecstsft.pasproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.projecstsft.pasproject.databinding.ActivityLoginBinding
import com.projecstsft.pasproject.databinding.ActivityRegisterBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var registerBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        auth = Firebase.auth

        val email = loginBinding.textEmail.text
        val password = loginBinding.textPassword.text

        title = "Autenticación"

        loginBinding.loginButton.setOnClickListener {
            checkData(email.toString(), password.toString())
        }

        loginBinding.signup.setOnClickListener{
            goRegister()
        }

        loginBinding.forgotpswd.setOnClickListener{

        }

    }

    private fun logIn(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    // Toast.makeText(this,"Operación Exitosa!", Toast.LENGTH_SHORT).show()
                    updateUI(user)
                    val intent =  Intent(this, MainActivity::class.java)
                    //intent.putExtra("name", registerBinding.regName.text.toString())
                    startActivity(intent)
                }
                else{
                    showError()
                    updateUI(null)
                }
            }
    }

    private fun showError(){
        Toast.makeText(baseContext, "Verifique los campos y/o datos.",
            Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(user: FirebaseUser?) {

    }
    private fun reload() {

    }
    private fun checkData(mail:String, password: String){
        when {
            mail.isEmpty() || password.isEmpty() -> {
                showError()
            }
            else -> {
                logIn(mail, password)
            }
        }
    }
    private fun goRegister(){
        loginBinding.signup.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun forgotPassword(){

    }
}