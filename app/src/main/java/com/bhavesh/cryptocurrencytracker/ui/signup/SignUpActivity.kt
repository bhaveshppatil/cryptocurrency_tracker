package com.bhavesh.cryptocurrencytracker.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivitySignUpBinding
import com.bhavesh.cryptocurrencytracker.ui.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivitySignUpBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        auth = Firebase.auth

        dataBinding.btnSignup.setOnClickListener {
            checkDataIsValidOrNot();
        }
    }

    private fun checkDataIsValidOrNot() {
        dataBinding.apply {
            val name = etNameUp.text.toString()
            val email = etEmailSignUp.text.toString()
            val number = etNumberUp.text.toString()
            val passwd = etPasswdUp.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && number.length == 10 && passwd.length >= 6) {
                registerNewUser(email, passwd)
            } else {
                showToast("Invalid formation entered")
            }
        }
    }

    private fun registerNewUser(email: String, passwd: String) {
        auth.createUserWithEmailAndPassword(email, passwd)
            .addOnCompleteListener(this@SignUpActivity) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    showToast("Authentication success.")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    showToast("Authentication failed.")
                }
            }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}