package com.example.glare.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.glare.BuildConfig
import com.example.glare.R
import com.example.glare.ui.components.Loader
import com.example.glare.ui.components.MoonEventLabel
import com.example.glare.ui.components.NightProgressArc
import com.example.glare.ui.theme.cardColor
import com.example.glare.ui.theme.primaryTextColor
import com.example.glare.utils.Size
import com.example.glare.utils.helper.formatDateTime
import com.example.glare.utils.helper.formatTime
import com.example.glare.utils.helper.getCurrDate
import com.example.glare.utils.helper.getCurrTime
import com.example.glare.utils.helper.getMoonTimeDiff
import com.example.glare.utils.helper.getTimeComparison
import com.example.glare.viewmodel.MoonlightViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NightScreen(
    modifier: Modifier = Modifier,
    latitude: Double,
    longitude: Double,
    navController: NavController,
) {

    val scrollState = rememberScrollState()
    val moonlightViewModel: MoonlightViewModel = viewModel()

    val moonlightData by moonlightViewModel.moonlightData.collectAsState()
    val magicLightsData by moonlightViewModel.magicLightsData.collectAsState()


    LaunchedEffect(Unit) {
        moonlightViewModel.fetchMoonlightData(
            key= BuildConfig.API_KEY,
            query = "$latitude,$longitude"
        )

        moonlightViewModel.fetchMagicLightsData(
            latitude = latitude,
            longitude = longitude,
            date = getCurrDate()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()

    ) {
        if (moonlightData == null || magicLightsData == null)
          Loader()
        else
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = Size.pdMd - 2.75.dp)
                    .padding(bottom = Size.pdSm)
            ) {
                moonlightData?.let {
                    val moonData = it.forecast.forecastday[0].astro

                    NightProgressArc(moonData.moonrise, moonData.moonset)

                    Box(
                        modifier = Modifier
                            .padding(
                                horizontal = Size.pdMd + 4.75.dp, vertical = Size.pdMd + 8.75.dp
                            )
                            .fillMaxSize()

                    ) {

                        Column {

                            Text(
                                moonData.moon_phase,
                                style = TextStyle(
                                    fontSize = 17.75.sp,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.padding(2.75.dp))
                            Text(
                                "${moonData.moon_illumination}% illumination",
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    color = Color(0xff938caa),
                                    fontSize = 14.75.sp
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                        }

                    }

                    Spacer(modifier = Modifier.padding(Size.pdMd - 1.25.dp))
                    Text(
                        "Today",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Size.pdMd - 0.25.dp),
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                    Text(
                        if (getTimeComparison(
                                moonData.moonrise,
                                getCurrTime()
                            ) && getTimeComparison(
                                getCurrTime(), moonData.moonset
                            )
                        )
                            if (getMoonTimeDiff(getCurrTime(), moonData.moonset)[0].toInt() == 0) {
                                "Moon sets in ${
                                    getMoonTimeDiff(
                                        getCurrTime(),
                                        moonData.moonset
                                    )[1]
                                } minutes"
                            } else {
                                "Moon sets in ${
                                    getMoonTimeDiff(
                                        getCurrTime(),
                                        moonData.moonset
                                    )[0]
                                } hours and ${
                                    getMoonTimeDiff(
                                        getCurrTime(),
                                        moonData.moonset
                                    )[1]
                                } minutes"
                            }
                        else {
                            if (getMoonTimeDiff(getCurrTime(), moonData.moonrise)[0].toInt() == 0) {
                                "Moon rises in ${
                                    getMoonTimeDiff(
                                        getCurrTime(),
                                        moonData.moonset
                                    )[1]
                                } minutes"
                            } else {
                                "Moon rises in ${
                                    getMoonTimeDiff(
                                        getCurrTime(),
                                        moonData.moonrise
                                    )[0]
                                } hours and ${
                                    getMoonTimeDiff(
                                        getCurrTime(),
                                        moonData.moonrise
                                    )[1]
                                } minutes"
                            }
                        }, style = TextStyle(textAlign = TextAlign.Center),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.padding(Size.pdMd - 1.25.dp))

                    MoonEventLabel("Moon Rise", formatTime(moonData.moonrise))
                    MoonEventLabel("Moon Set", formatTime(moonData.moonset))


                    HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))

                }

                magicLightsData?.let {
                    MoonEventLabel(
                        "Civil Twilight Begin",
                        formatDateTime(it.results.civil_twilight_begin)
                    )
                    MoonEventLabel(
                        "Civil Twilight End",
                        formatDateTime(it.results.civil_twilight_end)
                    )

                    HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))

                    MoonEventLabel(
                        "Nautical Twilight Begin",
                        formatDateTime(it.results.nautical_twilight_begin)
                    )
                    MoonEventLabel(
                        "Nautical Twilight End",
                        formatDateTime(it.results.nautical_twilight_end)
                    )

                    HorizontalDivider(thickness = 0.75.dp, color = Color(0xff2a2d39))

                    MoonEventLabel(
                        "Astronomical Twilight Begin",
                        formatDateTime(it.results.astronomical_twilight_begin)
                    )
                    MoonEventLabel(
                        "Astronomical Twilight End",
                        formatDateTime(it.results.astronomical_twilight_end)
                    )

                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = WindowInsets.statusBars.asPaddingValues()
                        .calculateTopPadding() + 5.25.dp,

                    )
                .padding(horizontal = 20.dp),
        ) {


            Icon(
                modifier = Modifier
                    .size(Size.bottomNavIcon + 4.dp)
                    .clip(CircleShape)
                    .background(color = primaryTextColor)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(4.dp),
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left_rg_line),
                contentDescription = null, tint = cardColor)
        }

    }
}
