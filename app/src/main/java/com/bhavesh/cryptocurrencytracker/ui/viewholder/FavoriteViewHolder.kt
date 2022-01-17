package com.bhavesh.cryptocurrencytracker.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bhavesh.cryptocurrencytracker.R
import com.bhavesh.cryptocurrencytracker.databinding.CryptoItemLayoutBinding
import com.bhavesh.cryptocurrencytracker.databinding.FavoriteItemLayoutBinding
import com.bhavesh.cryptocurrencytracker.remote.Currency
import com.bhavesh.cryptocurrencytracker.remote.response.Data
import com.bhavesh.cryptocurrencytracker.ui.clicklistener.OnItemClick
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class FavoriteViewHolder(
    private val itemLayoutBinding: FavoriteItemLayoutBinding,
    private val onItemClick: OnItemClick
) :
    RecyclerView.ViewHolder(itemLayoutBinding.root) {

    private val decimal = DecimalFormat("#.##")

    fun onBindData(currency: Currency) {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd-MM-yyyy")
        val date = input.parse(currency.last_date)
        val formattedDate = output.format(date)

        itemLayoutBinding.apply {
            tvSymbol.text = currency.symbol
            tvPrice.text = "$ ${decimal.format(currency.price)}"
            tvLastUpdated.text = formattedDate
            tvCurrencyName.text = currency.name

            ivFavorite.setOnClickListener {
                ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                onItemClick.removeItemFromFavorite(currency)
            }
        }
    }
}