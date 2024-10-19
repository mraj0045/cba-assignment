package com.cba.assignment.data.model

import com.google.gson.annotations.SerializedName

data class AtmDTO(
  @SerializedName("id")
  val id: String,

  @SerializedName("name")
  val name: String,

  @SerializedName("address")
  val address: String,

  @SerializedName("location")
  val location: Location
) {

  data class Location(
    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lng")
    val longitude: Double,
  )
}
