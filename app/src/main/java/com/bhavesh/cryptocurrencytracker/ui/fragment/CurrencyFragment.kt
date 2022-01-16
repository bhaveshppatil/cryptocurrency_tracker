package com.bhavesh.cryptocurrencytracker.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.remote.ConnectionLiveData
import com.bhavesh.cryptocurrencytracker.remote.Resource
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.adapter.CryptoAdapter
import com.bhavesh.cryptocurrencytracker.viewmodel.CryptoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currency.*


@AndroidEntryPoint
class CurrencyFragment : Fragment(R.layout.fragment_currency) {
    private lateinit var auth: FirebaseAuth
    private lateinit var cryptoAdapter: CryptoAdapter
    private val cryptoViewModel: CryptoViewModel by viewModels()
    private var dataList = mutableListOf<Data>()

    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDatFromApi()

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            toolbar.title = user.displayName
        } else {
            toolbar.title = "Welcome back"
        }

        checkNetworkConnectivity()
    }

    private fun checkNetworkConnectivity() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner, {
            if (it) {

            }
        })
    }

    private fun loadDatFromApi() {
        cryptoViewModel.getDataFromApi().observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Resource.Status.ERROR -> {
                    showToast("network error..")
                }
                Resource.Status.SUCCESS -> {
                    it.data?.data?.let {
                        dataList.addAll(it)
                        setRecyclerView()
                    }
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
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
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun setRecyclerView() {
        cryptoAdapter = context?.let { CryptoAdapter(it, dataList) }!!
        recycler_view.apply {
            adapter = cryptoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}