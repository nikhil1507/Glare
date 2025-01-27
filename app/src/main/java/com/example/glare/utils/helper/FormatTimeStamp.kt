package com.example.glare.utils.helper

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
fun timeStampFormat(timeStamp: String): String {
    val inputFormatter = SimpleDateFormat("h:mm:ss a", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

    return try {
        val dateTime = inputFormatter.parse(timeStamp)
        outputFormatter.format(dateTime).uppercase(Locale.getDefault())

    } catch (e: DateTimeParseException) {
        "..."
    }

}

fun formatTime(time: String): String {
    return if (time.startsWith("0")) {
        time.substring(1)
    } else {
        time
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(timeStamp: String): String {
    val parsedTime = ZonedDateTime.parse(timeStamp)
    val localTime = parsedTime.withZoneSameInstant(ZoneId.systemDefault())

    return localTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault()))
        .uppercase(
            Locale.ROOT
        )

}