package com.son.myapplication.data.model

data class SignatureResponse(
    val signature: String,
    val timestamp: Long,
    val apiKey: String
)
