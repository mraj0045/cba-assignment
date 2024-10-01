package com.cba.assignment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cba.assignment.common.extension.formatAsAmount
import com.cba.assignment.data.model.Transaction
import com.cba.assignment.databinding.FragmentTransactionDetailBinding
import com.cba.assignment.transactions.TransactionsAdapter

/**
 * This fragment is responsible to show the transaction details.
 */
class TransactionDetailFragment : Fragment() {

    private var _binding: FragmentTransactionDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val category: String by lazy {
        arguments?.getString("category").orEmpty()
    }
    private val description: String by lazy {
        arguments?.getString("description").orEmpty()
    }
    private val amount: Double by lazy {
        arguments?.getFloat("amount")?.toDouble() ?: 0.0
    }

    private val isPending: Boolean by lazy {
        arguments?.getBoolean("isPending") ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.transactionCategoryIcon.setImageResource(TransactionsAdapter.getIcon(category))
        binding.transactionDescriptionText.text =
            TransactionsAdapter.buildDescription(isPending, description)
        binding.transactionAmountText.text = amount.formatAsAmount()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}