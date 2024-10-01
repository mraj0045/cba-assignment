package com.cba.assignment.data.model

import com.google.gson.annotations.SerializedName

data class Transaction(

  @SerializedName("id")
  val id: String,

  @SerializedName("amount")
  val amount: Double,

  @SerializedName("isPending")
  val isPending: Boolean,

  @SerializedName("description")
  val description: String,

  @SerializedName("category")
  val category: String,

  @SerializedName("effectiveDate")
  val effectiveDate: String,

  val atmId: String? = null
)
