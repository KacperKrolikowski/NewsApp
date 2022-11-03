package com.krolikowski.data.responses.trendingnews


import com.google.gson.annotations.SerializedName

data class Provider(
    @SerializedName("name")
    val name: String,
    @SerializedName("_type")
    val type: String
)