package com.projecstsft.pasproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.projecstsft.pasproject.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        registerBinding =  ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        val email = registerBinding.regEmail.text
        val password = registerBinding.regPassword.text
        val name = registerBinding.regName.text
        val confirmpassword = registerBinding.regConfirm.text

        registerBinding.regBtn.setOnClickListener {
            checkData(name.toString(),email.toString(),password.toString(),confirmpassword.toString())
        }
        registerBinding.textloginIn.setOnClickListener{
            backLogin()
        }

    }

  private fun checkData(name:String,mail:String, password:String,confpswd:String){
      if (name.isNotEmpty() || mail.isNotEmpty() || password.isNotEmpty()|| confpswd.isNotEmpty()){

          FirebaseAuth.getInstance()
              .createUserWithEmailAndPassword(name.toString(),mail.toString())
              .addOnCompleteListener {
                 if( it.isSuccessful){
                    goHome(name, it.result.user?.email!!, ProviderType.BASIC)
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
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goHome(name:String, mail:String, provider:ProviderType){
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", mail)
            putExtra("name", registerBinding.regName.text)
            putExtra("Provider", provider.name)
        }
        startActivity(homeIntent)
    }



}
