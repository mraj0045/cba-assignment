package com.cba.assignment.common.extension

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val SERVER_DATE_FORMAT = "yyyy-MM-dd"
const val DATE_DISPLAY_FORMAT = "EEE dd MMM"

/**
 * Converts the given date from the local/remote data
 * to the display format `EEE dd MMM`
 *
 * Ex: If the date received from local/remote data is
 * 2021-02-26, then this function will return `Fri 26 Feb`
 */
fun String.dateToDisplayFormat(): String {
  val serverDateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
  val dateDisplayFormat = SimpleDateFormat(DATE_DISPLAY_FORMAT, Locale.getDefault())

  val serverDate = serverDateFormat.parse(this) ?: return ""

  return dateDisplayFormat.format(serverDate)
}

/**
 * Extension function to get the time ago string from
 * the local/remote data.
 *
 * Ex: If the date received from local/remote data is
 * 2021-02-26 (Assuming current date is 2021-02-28),
 * then this function will return `2 days ago`
 */
fun String.getTimeAgo(): String {
  val date: Date = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault()).parse(this) ?: return ""
  val duration = Calendar.getInstance().timeInMillis - date.time

  val yearsAgo = duration / TimeUnit.DAYS.toMillis(365)
  if (yearsAgo > 0) return "$yearsAgo years ago"

  val monthsAgo = duration / TimeUnit.DAYS.toMillis(30)
  if (monthsAgo > 0) return "$monthsAgo months ago"

  val daysAgo = duration / TimeUnit.DAYS.toMillis(1)
  if (daysAgo > 1) return "$daysAgo days ago"

  if (daysAgo == 0L) return "Today"
  return "Yesterday"
}