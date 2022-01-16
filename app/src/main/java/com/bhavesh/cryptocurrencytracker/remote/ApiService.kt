package com.bhavesh.cryptocurrencytracker.remote

import com.bhavesh.cryptocurrencytracker.remote.response.CryptoResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    //https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest

    @Headers("X-CMC_PRO_API_KEY: a72cd0cc-f070-4d69-bf62-d1a5c3d68ac0")
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptoData(): CryptoResponse
}