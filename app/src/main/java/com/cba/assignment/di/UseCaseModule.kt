package com.cba.assignment.di

import com.cba.assignment.data.source.remote.AccountRepository
import com.cba.assignment.domain.usecase.FetchAccountDetailsUseCase
import com.cba.assignment.domain.usecase.FetchAccountDetailsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun bindFetchAccountDetailsUseCase(
        repository: AccountRepository
    ): FetchAccountDetailsUseCase {
        return FetchAccountDetailsUseCaseImpl(repository)
    }
}
