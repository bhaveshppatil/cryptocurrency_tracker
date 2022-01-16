package com.bhavesh.cryptocurrencytracker.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.cryptocurrencytracker.databinding.CryptoItemLayoutBinding
import com.bhavesh.cryptocurrencytracker.remote.response.Data

class CryptoViewHolder(
    private val itemLayoutBinding: CryptoItemLayoutBinding
) :
    RecyclerView.ViewHolder(itemLayoutBinding.root) {

    fun onBindData(data: Data) {
        itemLayoutBinding.apply {
            tvSymbol.text = data.symbol
            tvPrice.text = "${data.quote.uSD.price}"
            tvLastUpdated.text = data.last_updated
            tvCurrencyName.text = data.name
        }
    }
}