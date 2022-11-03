package com.krolikowski.data.responses.trendingnews


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("text")
    val text: String,
    @SerializedName("_type")
    val type: String
)