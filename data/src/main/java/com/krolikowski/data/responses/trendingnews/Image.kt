package com.krolikowski.data.responses.trendingnews


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("provider")
    val provider: List<Provider>,
    @SerializedName("_type")
    val type: String,
    @SerializedName("url")
    val url: String
)