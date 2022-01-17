package com.bhavesh.cryptocurrencytracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.CryptoItemLayoutBinding
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import com.bhavesh.cryptocurrencytracker.ui.viewholder.CryptoViewHolder

class CryptoAdapter(
    var dataModelList: MutableList<Data>,
    val onItemClick: OnItemClick
) : RecyclerView.Adapter<CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val itemLayoutBinding: CryptoItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.crypto_item_layout,
            parent,
            false
        )
        return CryptoViewHolder(itemLayoutBinding, onItemClick)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val dataModel = dataModelList[position]
        holder.onBindData(dataModel)

    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    fun searchCurrencyInList(searchDataList: ArrayList<Data>) {
        dataModelList = searchDataList
        notifyDataSetChanged()
    }
}