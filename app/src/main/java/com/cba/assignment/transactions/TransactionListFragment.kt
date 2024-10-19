package com.cba.assignment.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cba.assignment.TransactionViewModel
import com.cba.assignment.databinding.FragmentTransactionListBinding
import kotlinx.coroutines.launch

/**
 * This fragment is responsible to show the transaction list.
 */
class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter: TransactionsAdapter by lazy {
        TransactionsAdapter { transaction ->
            findNavController().navigate(
                TransactionListFragmentDirections.actionTransactionListToTransactionDetails(
                    transaction
                )
            )
        }
    }

    private val transactionViewModel: TransactionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        observeTransactions()
    }

    private fun observeTransactions() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.progressBar.isVisible = true
            transactionViewModel.transactionsFlow().collect {
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.submitList(it) {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun setUpAdapter() {
        binding.transactionListView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            transactionViewModel.fetAccountDetails()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}