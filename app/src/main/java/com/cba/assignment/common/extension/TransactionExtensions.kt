package com.cba.assignment.common.extension

import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.cba.assignment.R
import com.cba.assignment.domain.model.Transaction


private const val SHOPPING = "shopping"
private const val BUSINESS = "business"
private const val CARDS = "cards"
private const val ENTERTAINMENT = "entertainment"
private const val GROCERIES = "groceries"
private const val EATING_OUT = "eatingOut"
private const val TRANSPORT = "transport"
private const val CASH = "cash"
private const val UN_CATEGORISED = "uncategorised"
private const val PENDING_TEXT = "PENDING: "

fun Transaction.getIcon(): Int {
    return when (this.category) {
        SHOPPING -> R.drawable.icon_category_shopping
        BUSINESS -> R.drawable.icon_category_business
        CARDS -> R.drawable.icon_category_cards
        ENTERTAINMENT -> R.drawable.icon_category_entertainment
        GROCERIES -> R.drawable.icon_category_groceries
        EATING_OUT -> R.drawable.icon_category_eating_out
        TRANSPORT -> R.drawable.icon_category_transportation
        CASH -> R.drawable.icon_category_cash
        UN_CATEGORISED -> R.drawable.icon_category_uncategorised
        else -> R.drawable.icon_category_uncategorised
    }
}

fun Transaction.buildDescription(): CharSequence {
    return buildSpannedString {
        if (isPending) {
            bold { append(PENDING_TEXT) }
            append(description)
        } else {
            append(description)
        }
    }
}
