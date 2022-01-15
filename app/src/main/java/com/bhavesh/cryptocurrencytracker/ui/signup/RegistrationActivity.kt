package com.bhavesh.cryptocurrencytracker.ui.signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivityRegistrationBinding
import com.bhavesh.cryptocurrencytracker.ui.login.LoginActivity

class RegistrationActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration)

        dataBinding.apply {
            btnLoginRegister.setOnClickListener {
                btnLoginRegister.setBackgroundResource(R.drawable.onclick_bg)
                btnLoginRegister.setTextColor(Color.WHITE)
                val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            btnSignupRegister.setOnClickListener {
                btnSignupRegister.setBackgroundResource(R.drawable.onclick_bg)
                btnSignupRegister.setTextColor(Color.WHITE)
                val intent = Intent(this@RegistrationActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}