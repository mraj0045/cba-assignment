package com.cba.assignment.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val id: String,
    val amount: Double,
    val isPending: Boolean,
    val description: String,
    val category: String,
    val effectiveDate: String,
    val atmId: String? = null
) : Parcelable
