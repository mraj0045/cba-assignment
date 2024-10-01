package com.cba.assignment.data.source.remote

import com.cba.assignment.data.model.AccountDetail
import retrofit2.http.GET

interface AccountDetailApiService {

    @GET("s/inyr8o29shntk9w/exercise.json?dl=1")
    suspend fun fetchAccountDetails(): AccountDetail
}
