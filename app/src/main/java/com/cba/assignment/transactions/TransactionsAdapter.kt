package com.cba.assignment.transactions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cba.assignment.common.extension.buildDescription
import com.cba.assignment.common.extension.dateToDisplayFormat
import com.cba.assignment.common.extension.formatAsAmount
import com.cba.assignment.common.extension.getIcon
import com.cba.assignment.common.extension.getTimeAgo
import com.cba.assignment.databinding.ListItemTransactionBinding
import com.cba.assignment.databinding.ListItemTransactionGroupBinding
import com.cba.assignment.domain.model.Transaction

class TransactionsAdapter(private val onClick: (Transaction) -> Unit) :
    ListAdapter<Any, ViewHolder>(diffUtil) {

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
                ), onClick
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is String -> TYPE_DATE
            else -> TYPE_TRANSACTION
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is DateViewHolder -> holder.bind(item as String)
            is TransactionViewHolder -> holder.bind(item as Transaction)
        }
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
        private val binding: ListItemTransactionBinding, val onClick: (Transaction) -> Unit
    ) : ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            with(transaction) {
                binding.transactionCategoryIcon.setImageResource(getIcon())
                binding.transactionDescriptionText.text = buildDescription()
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

        private val diffUtil = object : ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return when {
                    oldItem is String && newItem is String -> oldItem == newItem
                    oldItem is Transaction && newItem is Transaction -> oldItem.id == newItem.id
                    else -> oldItem == newItem
                }
            }
        }
    }

}