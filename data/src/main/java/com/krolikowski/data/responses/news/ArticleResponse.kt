package com.krolikowski.data.responses.news


import com.google.gson.annotations.SerializedName
import com.krolikowski.domain.entities.NewsEntity

data class ArticleResponse(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: SourceResponse,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String?
) {
    fun toEntity() = NewsEntity(
        title = this.title,
        author = this.author ?: "",
        description = this.description ?: "",
        webUrl = this.url,
        imageUrl = this.urlToImage ?: "",
        date = this.publishedAt
    )
}