package com.projecstsft.pasproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()

        val email : TextView = findViewById(R.id.RegEmail)
        val password : TextView = findViewById(R.id.regPassword)
        val btnregister : Button = findViewById(R.id.btnRegister)

        btnregister.setOnClickListener {
            when {
                password.text.toString().isEmpty() || email.text.toString().isEmpty() -> {
                    Toast.makeText(this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    registerUser(email.text.toString(), password.text.toString())
                }
            }

        }

    }

    fun onLinkClick(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registro exitoso, el usuario ha sido creado
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    // Realiza las acciones necesarias después del registro exitoso
                } else {
                    // Ocurrió un error durante el registro
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        // La dirección de correo electrónico no es válida
                    } catch (e: FirebaseAuthUserCollisionException) {
                        // La dirección de correo electrónico ya está en uso por otro usuario
                    } catch (e: Exception) {
                        // Otro tipo de error
                    }
                }
            }
    }

}
