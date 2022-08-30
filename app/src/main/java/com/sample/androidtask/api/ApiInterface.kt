package com.sample.androidtask.api

import com.sample.androidtask.models.CommonResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    companion object {
        var page=1
    }

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): CommonResponse

}
