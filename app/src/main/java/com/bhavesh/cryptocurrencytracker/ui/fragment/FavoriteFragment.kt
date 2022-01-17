package com.bhavesh.cryptocurrencytracker.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.FragmentFavoriteBinding
import com.bhavesh.cryptocurrencytracker.remote.Currency
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.adapter.FavoriteAdapter
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import com.google.firebase.database.*

class FavoriteFragment : Fragment(R.layout.fragment_favorite), OnItemClick {
    private lateinit var dataBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var databaseReference: DatabaseReference
    private var dataList = mutableListOf<Currency>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        getCurrencyListFromFirebase()
    }

    private fun setRecyclerView() {
        dataBinding.recyclerViewFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun getCurrencyListFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("currency")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (currencySnap in snapshot.children) {
                        val currency = currencySnap.getValue(Currency::class.java)
                        if (currency != null) {
                            dataList.add(currency)
                        }
                    }
                    favoriteAdapter = FavoriteAdapter(dataList, this@FavoriteFragment)
                    dataBinding.recyclerViewFavorite.adapter = favoriteAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun addItemToFavorite(data: Data) {
        TODO("Not yet implemented")
    }

    override fun removeItemFromFavorite(currency: Currency) {
        currency.name?.let {
            databaseReference.child(it).removeValue().addOnSuccessListener {
                showToast("item removed successfully")
                dataList.clear()
                favoriteAdapter.notifyDataSetChanged()
            }.addOnFailureListener {
                showToast("failed to remove item")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}