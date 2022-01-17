package com.bhavesh.cryptocurrencytracker.remote.response

data class CryptoModel(
    val `data`: List<Data>,
    val status: Status
)