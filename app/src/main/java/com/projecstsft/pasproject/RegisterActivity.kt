package com.projecstsft.pasproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projecstsft.pasproject.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        val db = Firebase.firestore
        firebaseAuth =  Firebase.auth
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

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
        if (name.isNotEmpty() && mail.isNotEmpty() && password.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener {
                    if( it.isSuccessful){
                       // val user = auth.currentUser
                        storeUserData(it.result?.user?.uid, mail)
                        Toast.makeText(
                            this,
                            "Registration successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        goHome(it.result?.user?.email?:"")
                    }else{
                        showAlert()
                    }
                }
        }
    }

    private fun storeUserData(uid: String?, email: Any) {
        val userDocument = firestore.collection("users").document(uid!!)
        val userData = hashMapOf("email" to email)

        userDocument.set(userData)
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Failed to store user data. ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun goHome(email:String){
        //Toast.makeText(this, name.toString(), Toast.LENGTH_SHORT).show()
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homeIntent)
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

private fun saveData(name: String, mail: String, password: String){
    val user = hashMapOf(
        "name" to name,
        "email" to mail,
        "password" to password
    )
    val db = Firebase.firestore
    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
}

}