package com.bhavesh.cryptocurrencytracker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bhavesh.cryptocurrencytracker.remote.Resource
import com.bhavesh.cryptocurrencytracker.remote.response.CryptoModel
import com.bhavesh.cryptocurrencytracker.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


/**
 * This is a bridge between our activity and Repository, where we are notifying activity(view)
 * about the response change via livedata
*/

@HiltViewModel
class CryptoViewModel @Inject constructor(private val cryptoRepository: CryptoRepository) :
    ViewModel() {

    fun getDataFromApi(): LiveData<Resource<CryptoModel>> {
        return liveData(Dispatchers.IO) {
            val data = cryptoRepository.getDataFromAPI()
            emit(data)
        }
    }
}