package com.cba.assignment.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cba.assignment.R
import com.cba.assignment.common.extension.dateToDisplayFormat
import com.cba.assignment.common.extension.formatAsAmount
import com.cba.assignment.common.extension.getTimeAgo
import com.cba.assignment.data.model.Transaction
import com.cba.assignment.databinding.ListItemTransactionBinding
import com.cba.assignment.databinding.ListItemTransactionGroupBinding

class TransactionsAdapter(private val onClick: (Transaction) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    private var transactions: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_DATE -> DateViewHolder(
                ListItemTransactionGroupBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> TransactionViewHolder(
                ListItemTransactionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                onClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = transactions[position]
        return when (item) {
            is String -> TYPE_DATE
            else -> TYPE_TRANSACTION
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = transactions[position]
        when (holder) {
            is DateViewHolder -> holder.bind(item as String)
            is TransactionViewHolder -> holder.bind(item as Transaction)
        }
    }

    fun setItems(list: List<Any>) {
        this.transactions = list
        notifyDataSetChanged()
    }

    class DateViewHolder(
        private val binding: ListItemTransactionGroupBinding
    ) : ViewHolder(binding.root) {

        fun bind(date: String) {
            binding.dateText.text = buildSpannedString {
                bold { append(date.dateToDisplayFormat()) }
                append(" ")
                append(date.getTimeAgo())
            }
        }
    }

    class TransactionViewHolder(
        private val binding: ListItemTransactionBinding,
        val onClick: (Transaction) -> Unit
    ) : ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            with(transaction) {
                binding.transactionCategoryIcon.setImageResource(getIcon(category))
                binding.transactionDescriptionText.text = buildDescription(isPending, description)
                binding.transactionAmountText.text = amount.formatAsAmount()
                binding.root.setOnClickListener {
                    onClick(transaction)
                }
            }
        }

    }

    companion object {

        private const val TYPE_DATE = 1
        private const val TYPE_TRANSACTION = 2
        fun getIcon(category: String): Int {
            return when (category) {
                "shopping" -> R.drawable.icon_category_shopping
                "business" -> R.drawable.icon_category_business
                "cards" -> R.drawable.icon_category_cards
                "entertainment" -> R.drawable.icon_category_entertainment
                "groceries" -> R.drawable.icon_category_groceries
                "eatingOut" -> R.drawable.icon_category_eating_out
                "transport" -> R.drawable.icon_category_transportation
                "cash" -> R.drawable.icon_category_cash
                "uncategorised" -> R.drawable.icon_category_uncategorised
                else -> R.drawable.icon_category_uncategorised
            }
        }

        fun buildDescription(isPending: Boolean, desc: String): CharSequence {
            return buildSpannedString {
                if (isPending) {
                    bold { append("PENDING:") }
                    append(desc)
                } else {
                    append(desc)
                }
            }
        }
    }

}