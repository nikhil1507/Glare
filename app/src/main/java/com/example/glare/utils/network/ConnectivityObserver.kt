package com.example.glare.utils.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val isConnected : Flow<Boolean>
}