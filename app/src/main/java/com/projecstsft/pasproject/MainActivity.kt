package com.projecstsft.pasproject

//import android.content.Intent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {

        val screenSplash = installSplashScreen()

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        //setContentView(R.layout.activity_login)
        val loginBtn: Button = findViewById(R.id.loginButton)
        val email : EditText = findViewById(R.id.editEmailText)
        val password: EditText = findViewById(R.id.editPassword)
        firebaseAuth = Firebase.auth
        /*if(email.text.isEmpty() or password.text.isEmpty()){
            Toast.makeText(baseContext, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        }else {
            loginBtn.setOnClickListener() {
                signIng(email.text.toString(),password.text.toString())
            }
        }*/
        loginBtn.setOnClickListener {
            signIng(email.text.toString(),password.text.toString())
        }
        /*Thread.sleep(1000)
        screenSplash.setKeepOnScreenCondition{true}*/
        /*val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()*/
    }
    private fun signIng(email:String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    //setContentView(R.layout.activity_main)
                    Toast.makeText(baseContext, user?.uid.toString(), Toast.LENGTH_SHORT).show()
                    val intnt = Intent(this, MainActivity::class.java)
                    startActivity(intnt)
                }
                else{
                    Toast.makeText(baseContext, "Verifique su email y/o password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}