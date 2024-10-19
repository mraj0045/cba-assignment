package com.cba.assignment.data.model

import com.google.gson.annotations.SerializedName

data class AccountDetailDTO(
    @SerializedName("account")
    val account: AccountDTO,

    @SerializedName("transactions")
    val transactions: List<TransactionDTO>,

    @SerializedName("atms")
    val atms: List<AtmDTO>
)
