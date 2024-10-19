package com.cba.assignment.di

import com.cba.assignment.data.source.remote.AccountDetailApiService
import com.cba.assignment.data.source.remote.AccountRepository
import com.cba.assignment.data.source.remote.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun bindsAccountRepository(
        accountDetailApiService: AccountDetailApiService, dispatcher: CoroutineDispatcher
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountDetailApiService = accountDetailApiService, dispatcher = dispatcher
        )
    }

}
