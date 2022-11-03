package com.krolikowski.data.responses.trendingnews


import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("image")
    val image: Image,
    @SerializedName("isBreakingNews")
    val isBreakingNews: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("newsSearchUrl")
    val newsSearchUrl: String,
    @SerializedName("query")
    val query: Query,
    @SerializedName("_type")
    val type: String,
    @SerializedName("webSearchUrl")
    val webSearchUrl: String
)