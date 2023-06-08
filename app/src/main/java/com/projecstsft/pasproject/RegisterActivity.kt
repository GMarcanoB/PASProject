package com.projecstsft.pasproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        val email : TextView = findViewById(R.id.RegEmail)
        val password : TextView = findViewById(R.id.regPassword)
        val newUser : Button = findViewById(R.id.btnRegister)
       /* val dirlogin : TextView = findViewById(R.id.loginIn)
        dirlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }*/
        newUser.setOnClickListener {
            when {
                password.text.toString().isEmpty() || email.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    newRegister(email.text.toString(), password.text.toString())
                }
            }

        }

    }

    private fun updateUI(user: FirebaseUser?) {

    }

    fun onLinkClick(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
     private fun newRegister(mail:String, pwd:String){
         auth.createUserWithEmailAndPassword(mail, pwd)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // Sign in success, update UI with the signed-in user's information
                     Log.d(TAG, "createUserWithEmail:success")
                     val user = auth.currentUser
                     updateUI(user)
                 } else {
                     // If sign in fails, display a message to the user.
                     Log.w(TAG, "createUserWithEmail:failure", task.exception)
                     Toast.makeText(
                         baseContext,
                         "Authentication failed.",
                         Toast.LENGTH_SHORT,
                     ).show()
                     updateUI(null)
                 }
             }
     }
}