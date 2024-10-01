package com.cba.assignment.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cba.assignment.MainViewModel
import com.cba.assignment.R
import com.cba.assignment.data.model.Transaction
import com.cba.assignment.databinding.FragmentTransactionListBinding
import com.cba.assignment.databinding.ListItemTransactionBinding
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
            findNavController().navigate(R.id.TransactionDetails, Bundle().apply {
                putString("category", transaction.category)
                putString("description", transaction.description)
                putFloat("amount", transaction.amount.toFloat())
                putBoolean("isPending", transaction.isPending)
            })
        }
    }

    private val mainViewModel: MainViewModel by activityViewModels()

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
            mainViewModel.transactionsFlow().collect {
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.setItems(it)
            }
        }
    }

    private fun setUpAdapter() {
        binding.transactionListView.adapter = adapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.fetAccountDetails()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}