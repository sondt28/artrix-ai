package com.son.myapplication.data.network.source

import com.son.myapplication.data.model.SignatureResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiSignatureDataService {

    @GET("/v2/styles")
    suspend fun getSignature() : Response<SignatureResponse>
}