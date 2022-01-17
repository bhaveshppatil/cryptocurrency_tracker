package com.bhavesh.cryptocurrencytracker.ui.clicklistener

import com.bhavesh.cryptocurrencytracker.remote.Currency
import com.bhavesh.cryptocurrencytracker.remote.response.Data

interface OnItemClick {

    fun addItemToFavorite(data: Data)
    fun removeItemFromFavorite(currency: Currency)

}