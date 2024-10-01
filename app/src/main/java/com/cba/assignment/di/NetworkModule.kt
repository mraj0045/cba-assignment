package com.cba.assignment.di

import com.cba.assignment.data.source.remote.AccountDetailApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideAccountDetailApiService(retrofit: Retrofit): AccountDetailApiService =
        retrofit.create(AccountDetailApiService::class.java)

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.dropbox.com/")
        .client(OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
        )
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
}
