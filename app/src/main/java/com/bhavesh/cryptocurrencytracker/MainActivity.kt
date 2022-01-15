package com.bhavesh.cryptocurrencytracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.databinding.ActivityMainBinding
import com.bhavesh.cryptocurrencytracker.ui.HomeActivity
import com.bhavesh.cryptocurrencytracker.ui.signup.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        dataBinding.btnGetStarted.setOnClickListener {
            val  user = auth.currentUser
            if (user != null){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}