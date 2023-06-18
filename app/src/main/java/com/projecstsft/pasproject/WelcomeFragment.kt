package com.projecstsft.pasproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class WelcomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var name: String? = null
    private var email: String? = null

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
            email = it.getString(EMAIL_BUNDLE)
        }
    }

       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    companion object {
        const val NAME_BUNDLE = "name_bundle"
        const val EMAIL_BUNDLE = "email_bundle"
        @JvmStatic
        fun newInstance(name: String, email: String) =
            WelcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_BUNDLE, name)
                    putString(EMAIL_BUNDLE, email)
                }
            }

    }

}