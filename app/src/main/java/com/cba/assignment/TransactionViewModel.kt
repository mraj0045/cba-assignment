package com.cba.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cba.assignment.domain.model.AccountDetail
import com.cba.assignment.domain.usecase.FetchAccountDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val fetchAccountDetailsUseCase: FetchAccountDetailsUseCase,
) : ViewModel() {

    private val accountDetailFlow = MutableSharedFlow<AccountDetail>()

    init {
        fetAccountDetails()
    }

    fun fetAccountDetails() {
        viewModelScope.launch {
            val result = fetchAccountDetailsUseCase.execute()
            result.onSuccess { accountDetail ->
                accountDetailFlow.emit(accountDetail)
            }
        }
    }

    fun accountDetailsFlow() = accountDetailFlow.asSharedFlow().map { it.account }.filterNotNull()

    fun transactionsFlow(): Flow<List<Any>> =
        accountDetailFlow.asSharedFlow().map { accountDetail ->
            val transactions = accountDetail.transactions
            val map = transactions.groupBy {
                it.effectiveDate
            }
            val newTransactions = mutableListOf<Any>()
            map.forEach { entry ->
                newTransactions.add(entry.key)
                newTransactions.addAll(entry.value)
            }
            newTransactions
        }

}