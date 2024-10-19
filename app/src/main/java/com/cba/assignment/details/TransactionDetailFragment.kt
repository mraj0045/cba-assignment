package com.cba.assignment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.cba.assignment.TransactionViewModel
import com.cba.assignment.common.extension.buildDescription
import com.cba.assignment.common.extension.formatAsAmount
import com.cba.assignment.common.extension.getIcon
import com.cba.assignment.databinding.FragmentTransactionDetailBinding
import com.cba.assignment.domain.model.Transaction

/**
 * This fragment is responsible to show the transaction details.
 */
class TransactionDetailFragment : Fragment() {

    private var _binding: FragmentTransactionDetailBinding? = null

    private val args: TransactionDetailFragmentArgs by navArgs<TransactionDetailFragmentArgs>()

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateTransactionDetailView(args.transaction)
    }

    private fun updateTransactionDetailView(transaction: Transaction) {
        with(binding) {
            transactionCategoryIcon.setImageResource(transaction.getIcon())
            transactionDescriptionText.text = transaction.buildDescription()
            transactionAmountText.text = transaction.amount.formatAsAmount()
        }
    }
}