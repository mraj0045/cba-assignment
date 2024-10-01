package com.cba.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cba.assignment.data.model.Account
import com.cba.assignment.data.model.Transaction
import com.cba.assignment.data.source.remote.AccountDetailApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountDetailApiService: AccountDetailApiService
) : ViewModel() {

    private val accountDetailsFlow: MutableSharedFlow<Account> = MutableSharedFlow()
    private val transactionsFlow: MutableSharedFlow<List<Transaction>> = MutableSharedFlow()

    fun fetAccountDetails() {
        viewModelScope.launch {
            val accountDetail = accountDetailApiService.fetchAccountDetails()
            accountDetailsFlow.emit(accountDetail.account)
            transactionsFlow.emit(accountDetail.transactions)
        }
    }

    fun accountDetailsFlow() = accountDetailsFlow.asSharedFlow().filterNotNull()

    fun transactionsFlow(): Flow<List<Any>> = transactionsFlow.asSharedFlow().map { list ->
        val map = list.groupBy {
            it.effectiveDate
        }
        val newList = mutableListOf<Any>()
        map.forEach { entry ->
            newList.add(entry.key)
            newList.addAll(entry.value)
        }
        newList
    }

}