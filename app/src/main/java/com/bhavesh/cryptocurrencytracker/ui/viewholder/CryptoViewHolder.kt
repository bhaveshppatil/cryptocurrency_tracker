package com.bhavesh.cryptocurrencytracker.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.CryptoItemLayoutBinding
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class CryptoViewHolder(
    private val itemLayoutBinding: CryptoItemLayoutBinding,
    private val onItemClick: OnItemClick
) :
    RecyclerView.ViewHolder(itemLayoutBinding.root) {

    private val decimal = DecimalFormat("#.##")
    fun onBindData(data: Data) {

        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd-MM-yyyy")
        val date = input.parse(data.last_updated)
        val formattedDate = output.format(date)

        itemLayoutBinding.apply {
            tvSymbol.text = data.symbol
            tvPrice.text = "$ ${decimal.format(data.quote.USD.price)}"
            tvLastUpdated.text = formattedDate
            tvCurrencyName.text = data.name
            ivFavorite.setOnClickListener {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                onItemClick.addItemToFavorite()
            }
        }
    }
}