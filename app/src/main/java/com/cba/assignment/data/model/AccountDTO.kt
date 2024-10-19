package com.cba.assignment.data.model

import com.google.gson.annotations.SerializedName

data class AccountDTO(

  @SerializedName("accountName")
  val accountName: String,

  @SerializedName("accountNumber")
  val accountNumber: String,

  @SerializedName("available")
  val availableAmount: Double,

  @SerializedName("balance")
  val balanceAmount: Double,

  @SerializedName("bsb")
  val bankStateBranchNumber: String
)
