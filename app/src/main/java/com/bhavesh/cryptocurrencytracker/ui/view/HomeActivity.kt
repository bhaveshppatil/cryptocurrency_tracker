package com.bhavesh.cryptocurrencytracker.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(dataBinding.toolbar)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            dataBinding.toolbar.title = user.displayName
        } else {
            dataBinding.toolbar.title = "Welcome back"
        }
    }
}