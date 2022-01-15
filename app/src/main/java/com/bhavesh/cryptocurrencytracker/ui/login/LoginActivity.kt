package com.bhavesh.cryptocurrencytracker.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivityLoginBinding
import com.bhavesh.cryptocurrencytracker.ui.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dataBinding: ActivityLoginBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        dataBinding.btnLoginUp.setOnClickListener {
            checkDataValidOrNot()
        }

    }

    private fun checkDataValidOrNot() {
        dataBinding.apply {
            progressCircular.visibility = View.VISIBLE
            val email = etEmail.text.toString()
            val passwd = etPasswd.text.toString()

            if (email.isNotEmpty() && passwd.length >= 6) {
                signInUser(email, passwd)
            } else {
                showToast("Invalid formation entered")
            }
        }
    }

    private fun signInUser(email: String, passwd: String) {
        auth.signInWithEmailAndPassword(email, passwd).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                dataBinding.progressCircular.visibility = View.GONE
                showToast("Authentication success.")
                Log.d("TAG", "createUserWithEmail:success")
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Log.w("TAG", "createUserWithEmail:failure", it.exception)
                showToast("Authentication failed.")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}