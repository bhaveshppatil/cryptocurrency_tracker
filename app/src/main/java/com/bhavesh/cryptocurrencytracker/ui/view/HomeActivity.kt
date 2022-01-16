package com.bhavesh.cryptocurrencytracker.ui.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.ActivityHomeBinding
import com.bhavesh.cryptocurrencytracker.remote.ConnectionLiveData
import com.bhavesh.cryptocurrencytracker.remote.Status
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.adapter.CryptoAdapter
import com.bhavesh.cryptocurrencytracker.viewmodel.CryptoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var dataList = mutableListOf<Data>()

    private lateinit var cryptoAdapter: CryptoAdapter
    private val cryptoViewModel: CryptoViewModel by viewModels()

    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var dataBinding: ActivityHomeBinding

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

        setRecyclerView()
        checkNetworkConnectivity()
    }

    private fun checkNetworkConnectivity() {
        connectionLiveData = ConnectionLiveData(this.application)
        connectionLiveData.observe(this, {
            if (it) {
                loadDatFromApi()
            }
        })

    }

    private fun loadDatFromApi() {
        cryptoViewModel.getDataFromApi().observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.data?.let {
                        dataList.addAll(it)
                        setRecyclerView()
                    }
                }
                Status.ERROR -> {
                    showToast("network error..")
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                showToast("currently not available")
            }
            R.id.search_bar -> {
                showToast("currently not available")
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val searchView = menu.findItem(R.id.search_bar)
        val searchManager = searchView.actionView as SearchView
        searchManager.queryHint = "search task here"

        searchManager.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setRecyclerView() {
        cryptoAdapter = CryptoAdapter(this, dataList)
        dataBinding.recyclerView.setHasFixedSize(true)
        dataBinding.recyclerView.adapter = cryptoAdapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}