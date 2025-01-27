package com.example.glare.ui


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.glare.ui.theme.GlareTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import androidx.navigation.compose.composable
import com.example.glare.ui.components.NetworkLoader
import com.example.glare.ui.screens.DayScreen
import com.example.glare.ui.screens.NightScreen
import com.example.glare.ui.theme.cardColor
import com.example.glare.viewmodel.ConnectivityViewModel
import com.example.glare.viewmodel.SunlightViewModel
import com.google.accompanist.permissions.shouldShowRationale
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.FusedLocationProviderClient
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@SuppressLint("NewApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GlareApp(context: Context, connectivityViewModel: ConnectivityViewModel = viewModel()) {

    // Setting status bar color

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = cardColor
    )


    val navController = rememberNavController()

    val dayViewModel: SunlightViewModel = viewModel()
    val locationViewModel: LocationViewModel = viewModel()

    val isNetworkConnected by connectivityViewModel.isConnected.collectAsState()



    val geocoder = Geocoder(LocalContext.current, Locale.getDefault())

    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )


    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayDate = dateFormatter.format(Date())

    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 1)

    val tomorrowDate = dateFormatter.format(calendar.time)

    var permissionMessage by remember { mutableStateOf("Glare needs location permissions for its working") }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            locationViewModel.fetchLocation(fusedLocationClient, geocoder)

            dayViewModel.fetchTodaySunlightData(
                locationViewModel.latitude.toString(),
                locationViewModel.longitude.toString(),
                todayDate
            )

            dayViewModel.fetchTomorrowSunlightData(
                locationViewModel.latitude.toString(),
                locationViewModel.longitude.toString(),
                tomorrowDate
            )
        } else {
            permissionMessage = "Please allow location permission access from app settings"
        }
    }


    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (!locationPermissionState.status.isGranted && locationPermissionState.status.shouldShowRationale) {
            locationPermissionState.launchPermissionRequest()

        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }


    }


    GlareTheme {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->

            if(!isNetworkConnected) {
                NetworkLoader()
            }

           else  NavHost(navController = navController,
                startDestination = "dayScreen",
                enterTransition = {
                    slideInHorizontally { it }
                },
                exitTransition = {
                    slideOutHorizontally { -it }
                },
                popEnterTransition = {
                    slideInHorizontally { -it }
                },
                popExitTransition = {
                    slideOutHorizontally { it }
                }
            ) {
                composable("dayScreen") {
                    DayScreen(
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
                        viewModel = dayViewModel,
                        location = locationViewModel.locationName,
                        navController = navController,
                        isPermissionGranted = locationPermissionState.status.isGranted,
                        permissionMessage = permissionMessage
                    )
                }
                composable("nightScreen") {
                    NightScreen(
                        modifier = Modifier.padding(innerPadding),
                        locationViewModel.latitude,
                        locationViewModel.longitude,
                        navController
                    )
                }
            }

        }


    }

}


class LocationViewModel : ViewModel() {
    var latitude by mutableDoubleStateOf(0.0)
    var longitude by mutableDoubleStateOf(0.0)
    var locationName by mutableStateOf("Loading...")

    fun fetchLocation(fusedLocationClient: FusedLocationProviderClient, geocoder: Geocoder) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {

                latitude = it.latitude
                longitude = it.longitude

                // Fetching current location name based on latitude and longitude

                val address =
                    try {
                        geocoder.getFromLocation(it.latitude, it.longitude, 1)
                            ?.firstOrNull()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }

                locationName = address?.locality ?: "Not found"

            }

        }
    }

}

