package com.example.glare.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glare.model.SunlightData
import com.example.glare.model.repository.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SunlightViewModel : ViewModel() {

    private val repository = ApiRepository()

    private val _todaySunlightData = MutableStateFlow<SunlightData?>(null)
    val todaySunlightData: StateFlow<SunlightData?> = _todaySunlightData

    private val _tomorrowSunlightData = MutableStateFlow<SunlightData?>(null)
    val tomorrowSunlightData: StateFlow<SunlightData?> = _tomorrowSunlightData


    fun fetchTodaySunlightData(latitude: String, longitude: String, date:String) {
        viewModelScope.launch {
            try {
                val data = repository.getSunlightInfo(latitude, longitude, date)
                _todaySunlightData.value = data
            } catch (e: Exception) {
                Log.d("Repo", "Error while fetching today sunlight data")
            }
        }
    }

    fun fetchTomorrowSunlightData(latitude: String,longitude: String,date: String) {
        viewModelScope.launch {
            try {
                val data = repository.getSunlightInfo(latitude, longitude, date)
                _tomorrowSunlightData.value = data
            } catch (e: Exception) {
                Log.d("Repo", "Error while fetching tomorrow sunlight data")
            }
        }
    }
}
