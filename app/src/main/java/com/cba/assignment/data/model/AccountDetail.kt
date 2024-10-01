package com.cba.assignment.data.model

import com.google.gson.annotations.SerializedName

data class AccountDetail(
  @SerializedName("account")
  val account: Account,

  @SerializedName("transactions")
  val transactions: List<Transaction>,

  @SerializedName("atms")
  val atms: List<Atm>
)