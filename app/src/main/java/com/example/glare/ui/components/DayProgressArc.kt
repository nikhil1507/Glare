package com.example.glare.ui.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap

import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.glare.R
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayProgressArc(sunrise: String, sunset: String) {
    val image: ImageBitmap = ImageBitmap.imageResource(R.drawable.sun)

    val current = LocalTime.now()

    val dayDuration = Duration.between(parseTime(sunrise), parseTime(sunset)).toMinutes().toFloat()
    val elapsedTime =
        Duration.between(parseTime(sunrise), current).toMinutes().toFloat()
    val progress = (elapsedTime / dayDuration).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val arcStartAngle = 180f
        val arcSweepAngle = 180f
        val strokeWidth = 20f




        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp)
        ) {

            val canvasWidth = size.width
            val canvasHeight = size.height

            val arcRadius = (canvasWidth/ 2) - (strokeWidth * 2f)


            val currentAngle = arcStartAngle + progress * arcSweepAngle
            val angleInRadians = Math.toRadians(currentAngle.toDouble())

            val iconX =  arcRadius + (arcRadius) * cos(angleInRadians).toFloat()
            val iconY =  (canvasHeight / 2.25f )+ (arcRadius) * sin(angleInRadians).toFloat()

            drawIntoCanvas {


                translate(top = canvasHeight / 2.75f ) {

                    val alpha = 0.35f

                    drawArc(
                       color = Color(0x2CC6C4C4),
                        startAngle = arcStartAngle,
                        sweepAngle = arcSweepAngle,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                        topLeft = Offset(0f, 0f),
                    )

                    drawArc(
                        brush = Brush.horizontalGradient(
                            0f to Color(0xfff58301),
                            0.15f to Color(0xff74befe),
                            0.9f to Color(0xff74befe),
                            1f to Color(0xfff58301),
                        ),
                        startAngle = arcStartAngle,
                        sweepAngle = progress * arcSweepAngle,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                        topLeft = Offset(0f, 0f)
                    )

                    scale(scaleX = 0.75f, scaleY = 0.75f) {
                        drawImage(
                            image = image,
                            topLeft = Offset(iconX, iconY),

                            colorFilter = ColorFilter.tint(
                                color = Color(0xfffed45e),
                                blendMode = BlendMode.SrcIn
                            ),

                            )
                    }
                }
            }


        }


    }

}


@RequiresApi(Build.VERSION_CODES.O)
private fun parseTime(time: String): LocalTime {

    val format = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    val date: Date = format.parse(time) ?: throw IllegalArgumentException("Invalid time format")

    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()

}

