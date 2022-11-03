package com.krolikowski.data.responses.trendingnews


import com.google.gson.annotations.SerializedName

data class TrendingNewsResponse(
    @SerializedName("_type")
    val type: String,
    @SerializedName("value")
    val value: List<Value>
)