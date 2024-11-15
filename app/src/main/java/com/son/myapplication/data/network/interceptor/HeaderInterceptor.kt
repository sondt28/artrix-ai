package com.son.myapplication.data.network.interceptor

import okhttp3.Interceptor

val headerInterceptor = Interceptor { chain ->
    val request = chain.request().newBuilder()
        .addHeader("x-api-signature", "")
        .addHeader("x-api-timestamp", "")
        .addHeader("x-api-keyid", "")
        .build()

    chain.proceed(request)
}