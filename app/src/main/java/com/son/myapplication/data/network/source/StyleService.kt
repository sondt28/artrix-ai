package com.son.myapplication.data.network.source

import com.son.myapplication.data.model.StyleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StyleService {
    @GET("v2/styles")
    suspend fun getStyles(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100,
        @Query("project") project: String = "Artimind"
    ): Response<StyleResponse>
}