package com.bhavesh.cryptocurrencytracker.remote.response

data class Data(
    val cmc_rank: Int,
    val date_added: String,
    val id: Int,
    val last_updated: String,
    val name: String,
    val num_market_pairs: Int,
    val platform: Any,
    val quote: Quote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
)