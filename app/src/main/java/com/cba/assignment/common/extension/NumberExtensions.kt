package com.cba.assignment.common.extension

import java.text.NumberFormat
import java.util.*

/**
 * Extension function to format the amount.
 *
 * Ex: If the amount is 14.19 this function will
 * return $14.19 as output.
 */
fun Double.formatAsAmount(locale: Locale = Locale.US): String {
  val format: NumberFormat = NumberFormat.getCurrencyInstance(locale)
  format.maximumFractionDigits = 2
  return format.format(this)
}