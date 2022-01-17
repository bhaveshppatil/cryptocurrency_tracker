package com.bhavesh.cryptocurrencytracker.repository

import com.bhavesh.cryptocurrencytracker.remote.ApiService
import com.bhavesh.cryptocurrencytracker.remote.Resource
import com.bhavesh.cryptocurrencytracker.remote.ResponseHandler
import com.bhavesh.cryptocurrencytracker.remote.response.CryptoModel
import javax.inject.Inject


/**
 * Repository which gives us data from api
*/
class CryptoRepository @Inject constructor(private val apiService: ApiService) {

    private val responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getDataFromAPI(): Resource<CryptoModel> {
        return try {
            val cryptoModel: CryptoModel = apiService.getCryptoData()
            responseHandler.handleSuccess(cryptoModel)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}
