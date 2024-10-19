package com.cba.assignment.domain.usecase

import com.cba.assignment.data.model.AccountDTO
import com.cba.assignment.data.model.AccountDetailDTO
import com.cba.assignment.data.model.TransactionDTO
import com.cba.assignment.data.source.remote.AccountRepository
import com.cba.assignment.domain.model.Account
import com.cba.assignment.domain.model.AccountDetail
import com.cba.assignment.domain.model.Transaction

interface FetchAccountDetailsUseCase {

    suspend fun execute(): Result<AccountDetail>
}

class FetchAccountDetailsUseCaseImpl(
    private val repository: AccountRepository
) : FetchAccountDetailsUseCase {

    override suspend fun execute(): Result<AccountDetail> {
        return repository.fetchAccountDetails().map { it.map() }
    }

    private fun AccountDetailDTO.map(): AccountDetail {
        return AccountDetail(
            account = account.map(),
            transactions = transactions.map()
        )
    }

    private fun AccountDTO.map(): Account {
        return Account(
            accountName = accountName,
            accountNumber = accountNumber,
            availableAmount = availableAmount,
            balanceAmount = balanceAmount,
            bankStateBranchNumber = bankStateBranchNumber
        )
    }

    private fun List<TransactionDTO>.map(): List<Transaction> {
        return this.map {
            Transaction(
                id = it.id,
                amount = it.amount,
                isPending = it.isPending,
                description = it.description,
                category = it.category,
                effectiveDate = it.effectiveDate,
                atmId = it.atmId
            )
        }
    }
}