package com.example.glare.utils.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


fun getMoonTimeDiff(startTime: String, endTime: String): List<Long> {
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())


    val startDate = dateFormat.parse(startTime)
    val endDate = dateFormat.parse(endTime)


    val endTimeMillis = if (endDate!!.time < startDate!!.time) {
        endDate.time + TimeUnit.DAYS.toMillis(1)
    } else {
        endDate.time
    }

    val differenceInMillis = endTimeMillis - startDate.time

    val hours = TimeUnit.MILLISECONDS.toHours(differenceInMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(differenceInMillis) % 60

    return listOf(hours,minutes)
}



fun getCurrTime(): String {
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentTime = Date()

    val currentTimeFormatted = timeFormat.format(currentTime)
    return currentTimeFormatted
}

fun getCurrDate() : String{
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormatter.format(Date())
}

fun getTimeComparison(time1: String, time2: String) : Boolean {
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val parsedTime1 = formatter.parse(time1)
    val parsedTime2 = formatter.parse(time2)

    return parsedTime1.before(parsedTime2)

}