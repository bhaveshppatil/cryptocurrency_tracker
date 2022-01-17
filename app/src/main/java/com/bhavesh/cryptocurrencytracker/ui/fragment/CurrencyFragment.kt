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
import com.bhavesh.cryptocurrencytracker.remote.Currency
import com.bhavesh.cryptocurrencytracker.remote.Status
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.adapter.CryptoAdapter
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import com.bhavesh.cryptocurrencytracker.ui.viewmodel.CryptoViewModel
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
    private lateinit var database: DatabaseReference

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
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        dataBinding.apply {
            tvUsername.text = user?.displayName
            Glide.with(ivProfile).load(user?.photoUrl).into(ivProfile)
            ivSearch.setOnClickListener {
                searchBar.visibility = View.VISIBLE
            }
        }
        dataBinding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                dataBinding.searchBar.visibility = View.GONE
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

    override fun addItemToFavorite(data: Data) {

        database = FirebaseDatabase.getInstance().getReference("currency")
        val currency = Currency(data.name, data.symbol, data.last_updated, data.quote.USD.price)
        database.child(data.name).setValue(currency).addOnSuccessListener {
            showToast("item added to favorite..")
        }.addOnFailureListener {
            showToast("failed to add item..")
        }
    }

    override fun removeItemFromFavorite(currency: Currency) {
        TODO("Not yet implemented")
    }
}