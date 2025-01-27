package com.example.glare.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.glare.R
import com.example.glare.ui.components.DayProgressArc
import com.example.glare.ui.components.EventTimeLabel
import com.example.glare.ui.components.Loader
import com.example.glare.ui.theme.amberTextColor
import com.example.glare.ui.theme.blueAccentTextColor
import com.example.glare.ui.theme.cardColor
import com.example.glare.ui.theme.greenAccentTextColor
import com.example.glare.ui.theme.purpleAccentTextColor
import com.example.glare.ui.theme.redAccentTextColor
import com.example.glare.utils.Size
import com.example.glare.utils.helper.timeStampFormat
import com.example.glare.viewmodel.SunlightViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayScreen(
    modifier: Modifier = Modifier,
    viewModel: SunlightViewModel,
    location: String, navController: NavController,
    isPermissionGranted: Boolean,
    permissionMessage: String,
) {

    val scrollState = rememberScrollState()
    val scrollY = scrollState.value
    val maxScroll = 100f

    val alpha = (scrollY / maxScroll).coerceIn(0f, 1f)

    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 200)
    )

    val todaySunlightData by viewModel.todaySunlightData.collectAsState()
    val tomorrowSunlightData by viewModel.tomorrowSunlightData.collectAsState()


    Box(
        modifier = Modifier
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
            .fillMaxSize()
            .wrapContentSize()


    ) {
        if (!isPermissionGranted) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    permissionMessage,
                    style = TextStyle(fontSize = Size.fontMd, textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )


            }
        } else {
            if (todaySunlightData == null || tomorrowSunlightData == null) {
                Loader()
            } else Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = Size.pdMd - 2.75.dp)
                    .padding(bottom = Size.pdSm)
            ) {
                todaySunlightData?.let {
                    DayProgressArc(sunrise = it.results.sunrise, sunset = it.results.sunset)
                }
                Text(
                    "Today",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Size.pdSm, bottom = Size.pdMd - 0.25.dp),
                    style = TextStyle(textAlign = TextAlign.Center)
                )

                todaySunlightData?.let {
                    val result = it.results
                    InfoLayout(
                        result.sunrise,
                        result.dawn,
                        result.first_light,
                        result.golden_hour,
                        result.solar_noon,
                        result.sunset,
                        result.dusk,
                        result.last_light
                    )
                }

                Text(
                    "Tomorrow",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Size.pdMd + 1.75.dp, bottom = Size.pdMd - 0.25.dp),
                    style = TextStyle(textAlign = TextAlign.Center)
                )

                tomorrowSunlightData?.let {
                    val result = it.results
                    InfoLayout(
                        result.sunrise,
                        result.dawn,
                        result.first_light,
                        result.golden_hour,
                        result.solar_noon,
                        result.sunset,
                        result.dusk,
                        result.last_light
                    )

                }
            }

            // Top nav bar
            TopNavBar(location, navController, animatedAlpha)
        }


    }
}


@SuppressLint("NewApi")
@Composable
fun InfoLayout(
    sunrise: String,
    dawn: String,
    firstLight: String,
    goldenHour: String,
    solarNoon: String,
    sunset: String,
    dusk: String,
    lastLight: String,
) {
    Column {

        // Sunrise,fist light etc. events
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(color = cardColor)
                .fillMaxWidth()

        ) {
            Column {
                EventTimeLabel(
                    "Sunrise",
                    timeStampFormat(sunrise),
                    timeColor = greenAccentTextColor
                )
                HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))
                EventTimeLabel("Dawn", timeStampFormat(dawn), timeColor = redAccentTextColor)
                HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))
                EventTimeLabel(
                    "First Light",
                    timeStampFormat(firstLight),
                    timeColor = blueAccentTextColor
                )
            }


        }

        // Golden hour
        Spacer(modifier = Modifier.padding(Size.pdMd - 1.25.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(color = cardColor)
                .fillMaxWidth()
        )
        {
            EventTimeLabel(
                "Golden Hour",
                timeStampFormat(goldenHour),
                timeColor = amberTextColor
            )
        }

        HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))

        // Solar noon
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(color = cardColor)
                .fillMaxWidth()
        )
        {

            EventTimeLabel(
                "Solar Noon", timeStampFormat(solarNoon),
                timeColor = purpleAccentTextColor
            )
        }

        // Sunset, last light etc. events
        Spacer(modifier = Modifier.padding(Size.pdMd - 1.25.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(color = cardColor)
                .fillMaxWidth()
        )
        {

            Column {
                EventTimeLabel(
                    "Sunset",
                    timeStampFormat(sunset),
                    timeColor = greenAccentTextColor
                )
                HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))
                EventTimeLabel("Dusk", timeStampFormat(dusk), timeColor = amberTextColor)
                HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))
                EventTimeLabel(
                    "Last Light",
                    timeStampFormat(lastLight),
                    timeColor = purpleAccentTextColor
                )
            }
        }
    }
}


@Composable
private fun TopNavBar(
    location: String,
    navController: NavController,
    animatedAlpha: Float
) {
    Box(
        modifier = Modifier
            .background(cardColor.copy(alpha = animatedAlpha))
            .fillMaxWidth()
            .padding(
                vertical = Size.pdSm,
                horizontal = 20.dp

            )

    ) {


        Box(modifier = Modifier.align(Alignment.Center)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(Size.bottomNavIcon),
                    imageVector = ImageVector.vectorResource(R.drawable.location_fill),
                    contentDescription = null,
                    tint = Color(0xff30c0f4),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(location)
            }
        }

        Icon(
            modifier = Modifier
                .size(Size.bottomNavIcon + 4.dp)
                .clip(CircleShape)
                .align(Alignment.CenterEnd)
                .clickable {
                    navController.navigate("nightScreen")
                }
                .padding(4.dp),
            imageVector = ImageVector.vectorResource(R.drawable.moon_cloud_rg_line),
            contentDescription = null
        )


    }
}


