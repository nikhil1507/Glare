package com.example.glare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.glare.ui.GlareApp
import com.example.glare.utils.network.NetworkConnectivityObserver
import com.example.glare.viewmodel.ConnectivityViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        setContent {

            val connectivityViewModel = viewModel<ConnectivityViewModel> {


                ConnectivityViewModel(
                    connectivityObserver = NetworkConnectivityObserver(
                        context = applicationContext
                    )
                )
            }

            GlareApp(context = applicationContext, connectivityViewModel)

        }
    }
}