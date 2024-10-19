package com.cba.assignment.domain.model

data class AccountDetail(
    val account: Account, val transactions: List<Transaction>
)