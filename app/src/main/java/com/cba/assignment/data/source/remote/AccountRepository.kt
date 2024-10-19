package com.cba.assignment.data.source.remote

import com.cba.assignment.data.model.AccountDetailDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface AccountRepository {

    suspend fun fetchAccountDetails(): Result<AccountDetailDTO>
}

class AccountRepositoryImpl(
    private val accountDetailApiService: AccountDetailApiService,
    private val dispatcher: CoroutineDispatcher
) : AccountRepository {

    override suspend fun fetchAccountDetails(): Result<AccountDetailDTO> = withContext(dispatcher) {
        runCatching {
            accountDetailApiService.fetchAccountDetails()
        }
    }
}
