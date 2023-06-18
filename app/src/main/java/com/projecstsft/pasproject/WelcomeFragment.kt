package com.projecstsft.pasproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser

class WelcomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var name: String? = null
    private var email: String? = null
    private lateinit var textwelcome : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
               Log.w(TAG, "Error getting documents.", exception)
            }
        arguments?.let {
            name = it.getString(NAME_BUNDLE)
        }
    }

       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
           var textWelcome: String = R.id.textHello.toString()
           textWelcome = auth.currentUser?.email.toString()
        return inflater.inflate(R.layout.fragment_welcome, container, false)

    }

    companion object {
        val ADDREES_BUNDLE = "email"
        val NAME_BUNDLE = "name"
        @JvmStatic
        fun newInstance(name: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_BUNDLE, "name")
                }
            }

    }

}

