package com.son.myapplication.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.son.myapplication.data.network.interceptor.headerInterceptor
import com.son.myapplication.data.network.interceptor.loggingInterceptor
import com.son.myapplication.data.network.source.ApiSignatureDataService
import com.son.myapplication.data.network.source.ImageGenerationService
import com.son.myapplication.data.network.source.StyleService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL_SIGNATURE_SERVICE = "https://image-generator.dev.apero.vn"
const val BASE_URL_STYLE_SERVICE = "https://style-management-api.dev.apero.vn"
const val BASE_URL_GENERATE_SERVICE = "https://image-generator.dev.apero.vn"

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

fun provideSignatureService(): ApiSignatureDataService {
    return buildRetrofit(BASE_URL_SIGNATURE_SERVICE).create(ApiSignatureDataService::class.java)
}

fun provideStyleService(): StyleService {
    return buildRetrofit(BASE_URL_STYLE_SERVICE).create(StyleService::class.java)
}

fun provideGenerateService(): ImageGenerationService {
    return buildRetrofit(BASE_URL_GENERATE_SERVICE).create(ImageGenerationService::class.java)
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




