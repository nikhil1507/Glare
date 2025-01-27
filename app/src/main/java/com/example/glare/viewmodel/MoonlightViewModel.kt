package com.example.glare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glare.model.MagicLightsData
import com.example.glare.model.MoonlightData
import com.example.glare.model.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoonlightViewModel : ViewModel() {
    private val repository = ApiRepository()

    private val _moonlightData = MutableStateFlow<MoonlightData?>(null)
    val moonlightData: StateFlow<MoonlightData?> = _moonlightData

    private val _magicLightsData = MutableStateFlow<MagicLightsData?>(null)
    val magicLightsData: StateFlow<MagicLightsData?> = _magicLightsData

    fun fetchMoonlightData(key: String, query: String) {
        viewModelScope.launch {
            try {
                val data = repository.getMoonlightInfo(key, query)
                _moonlightData.value = data
            } catch (e: Exception) {
                Log.d("Repo", "Error while fetching moonlight data")
            }
        }
    }

    fun fetchMagicLightsData(latitude: Double, longitude: Double, date: String) {
        viewModelScope.launch {
            try {
                val data = repository.getMagicLights(latitude, longitude, date)
                _magicLightsData.value = data
            } catch (e: Exception) {
                Log.d("Repo", "Error while fetching magic lights data")
            }
        }

    }

}