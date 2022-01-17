package com.bhavesh.cryptocurrencytracker.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.FragmentCurrencyBinding
import com.bhavesh.cryptocurrencytracker.remote.ConnectionLiveData
import com.bhavesh.cryptocurrencytracker.remote.Status
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.adapter.CryptoAdapter
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import com.bhavesh.cryptocurrencytracker.ui.viewmodel.CryptoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_currency.*


@AndroidEntryPoint
class CurrencyFragment : Fragment(R.layout.fragment_currency), OnItemClick {
    private lateinit var auth: FirebaseAuth
    private lateinit var cryptoAdapter: CryptoAdapter
    private val cryptoViewModel: CryptoViewModel by viewModels()
    private var dataList = mutableListOf<Data>()
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var dataBinding: FragmentCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_currency, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNetworkConnectivity()

        dataBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchCurrency(newText.toString())
                return false
            }
        })
    }

    private fun checkNetworkConnectivity() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner, {
            if (it) {
                loadDatFromApi()
            } else {
                showToast("check internet connection..")
            }
        })
    }

    private fun loadDatFromApi() {
        dataBinding.progressBar.visibility = View.VISIBLE
        cryptoViewModel.getDataFromApi().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> {
                    showToast("network error..")
                    dataBinding.progressBar.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    it.data?.data?.let {
                        dataList.addAll(it)
                        dataBinding.progressBar.visibility = View.GONE
                        setRecyclerView()
                    }
                }
            }
        })
    }

    private fun setRecyclerView() {
        cryptoAdapter = CryptoAdapter(dataList, this)
        recycler_view.apply {
            adapter = cryptoAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    private fun searchCurrency(query: String) {
        val resultList = ArrayList<Data>()
        for (data in dataList) {
            if (data.name.toLowerCase().contains(query.toLowerCase())) {
                resultList.add(data)
            }
        }
        if (resultList.isEmpty()) {
            showToast("currency not found..")
        } else {
            cryptoAdapter.searchCurrencyInList(resultList)
        }
    }

    override fun addItemToFavorite() {
        showToast("item added to favorite..")
    }
}