package com.krolikowski.data.responses.news


import com.google.gson.annotations.SerializedName
import com.krolikowski.domain.entities.NewsListEntity

data class NewsListResponse(
    @SerializedName("articles")
    val articles: List<ArticleResponse>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
) {
    fun toEntity() = NewsListEntity(articles.map {
        it.toEntity()
    })
}