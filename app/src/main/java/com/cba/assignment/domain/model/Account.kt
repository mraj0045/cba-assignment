package com.cba.assignment.domain.model

data class Account(
    val accountName: String,
    val accountNumber: String,
    val availableAmount: Double,
    val balanceAmount: Double,
    val bankStateBranchNumber: String
)
