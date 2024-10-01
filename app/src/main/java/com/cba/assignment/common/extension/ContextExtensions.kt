package com.cba.assignment.common.extension

import android.content.Context

/**
 * Extension to read the data from the assets folder and
 * returns in String format.
 */
fun Context.readJsonAsset(fileName: String): String {
  val inputStream = assets.open(fileName)
  val size = inputStream.available()
  val buffer = ByteArray(size)
  inputStream.read(buffer)
  inputStream.close()
  return String(buffer, Charsets.UTF_8)
}