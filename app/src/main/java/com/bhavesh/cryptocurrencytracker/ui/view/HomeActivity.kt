package com.bhavesh.cryptocurrencytracker.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.ui.fragment.CurrencyFragment
import com.bhavesh.cryptocurrencytracker.ui.fragment.FavoriteFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home_bottom -> {
                    loadFragment(CurrencyFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.favorite_bottom -> {
                    loadFragment(FavoriteFragment())
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment).addToBackStack("add_frag").commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}