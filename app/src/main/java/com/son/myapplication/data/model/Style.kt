package com.son.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class StyleResponse(
    val data: Data
)

data class Data(
    val items: List<Style>
)

data class Style(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("aiFamily")
    val aiFamily: String,
)