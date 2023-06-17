package com.projecstsft.pasproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.projecstsft.pasproject.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        firebaseAuth =  Firebase.auth
        super.onCreate(savedInstanceState)
        registerBinding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        val email = registerBinding.regEmail.text
        val password = registerBinding.regPassword.text
        val name = registerBinding.regName.text

        registerBinding.regBtn.setOnClickListener {
            checkData(name.toString(),email.toString(),password.toString())
        }
        registerBinding.textloginIn.setOnClickListener{
            backLogin()
        }

    }

    private fun checkData(name:String, mail:String, password:String){
        if (name.isNotEmpty() || mail.isNotEmpty() || password.isNotEmpty()){
            Toast.makeText(this, "entró acá", Toast.LENGTH_SHORT).show()
            firebaseAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener {
                    if( it.isSuccessful){
                        goHome(name)
                    }else{
                        showAlert()
                    }
                }
        }
    }
    private fun showAlert(){
        val builder =  AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error registrando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun backLogin(){
        registerBinding.textloginIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goHome(name:String){
        Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show()
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("name", registerBinding.regName.text.toString())
        }
        startActivity(homeIntent)
    }



}