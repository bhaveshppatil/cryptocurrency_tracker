package com.bhavesh.cryptocurrencytracker.repository

import com.bhavesh.cryptocurrencytracker.remote.ApiService
import com.bhavesh.cryptocurrencytracker.remote.Resource
import com.bhavesh.cryptocurrencytracker.remote.ResponseHandler
import com.bhavesh.cryptocurrencytracker.remote.response.CryptoResponse
import javax.inject.Inject

class CryptoRepository @Inject constructor(val apiService: ApiService) {

    private var responseHandler: ResponseHandler = ResponseHandler()

    suspend fun getDataFromApi(): Resource<CryptoResponse> {
        return try {
            val response: CryptoResponse = apiService.getCryptoData()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}