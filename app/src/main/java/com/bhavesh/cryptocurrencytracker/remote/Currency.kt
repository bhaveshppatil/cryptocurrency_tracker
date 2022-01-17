package com.bhavesh.cryptocurrencytracker.remote

/*data class Currency(
    val name: String,
    val symbol: String,
    val last_date: String,
    val price: Double,
) */

data class Currency(
    var name: String? = null,
    var symbol: String? = null,
    var last_date: String? = null,
    val price: Double? = 0.0
)
