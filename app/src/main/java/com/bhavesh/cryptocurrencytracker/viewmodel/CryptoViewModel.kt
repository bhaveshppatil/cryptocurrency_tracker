package com.bhavesh.cryptocurrencytracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bhavesh.cryptocurrencytracker.remote.Resource
import com.bhavesh.cryptocurrencytracker.remote.response.CryptoResponse
import com.bhavesh.cryptocurrencytracker.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(var cryptoRepository: CryptoRepository) : ViewModel() {

    fun getDataFromApi(): LiveData<Resource<CryptoResponse>> {
        return liveData(Dispatchers.IO) {
            val data = cryptoRepository.getDataFromApi()
            emit(data)
        }
    }
}