package com.son.myapplication.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.son.myapplication.data.network.interceptor.headerInterceptor
import com.son.myapplication.data.network.interceptor.loggingInterceptor
import com.son.myapplication.data.network.source.ApiSignatureDataService
import com.son.myapplication.data.network.source.ImageGenerationService
import com.son.myapplication.data.network.source.StyleService
import com.son.myapplication.util.Constant.BASE_URL_GENERATE_SERVICE
import com.son.myapplication.util.Constant.BASE_URL_SIGNATURE_SERVICE
import com.son.myapplication.util.Constant.BASE_URL_STYLE_SERVICE
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun buildGson(): Gson = GsonBuilder().setLenient().create()

fun buildOkHttpClient() = OkHttpClient.Builder()
    .addInterceptor(headerInterceptor)
    .addInterceptor(loggingInterceptor)
    .build()

fun buildRetrofit(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(buildGson()))
        .baseUrl(baseUrl)
        .client(buildOkHttpClient())
        .build()
}

object ApiService {
    val generateService: ImageGenerationService by lazy {
        buildRetrofit(BASE_URL_GENERATE_SERVICE).create(ImageGenerationService::class.java)
    }

    val signatureService: ApiSignatureDataService by lazy {
        buildRetrofit(BASE_URL_SIGNATURE_SERVICE).create(ApiSignatureDataService::class.java)
    }

    val styleService: StyleService by lazy {
        buildRetrofit(BASE_URL_STYLE_SERVICE).create(StyleService::class.java)
    }
}




