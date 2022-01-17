package com.bhavesh.cryptocurrencytracker.remote

import com.bhavesh.cryptocurrencytracker.remote.response.CryptoModel
import retrofit2.http.GET
import retrofit2.http.Headers

/* This interface use for api calling , we want to fetch only data from api thats why we
will use get request
 */

interface ApiService {

    //https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest

    @Headers("X-CMC_PRO_API_KEY: a72cd0cc-f070-4d69-bf62-d1a5c3d68ac0")
    @GET("v1/cryptocurrency/listings/latest")
    suspend fun getCryptoData(): CryptoModel

}