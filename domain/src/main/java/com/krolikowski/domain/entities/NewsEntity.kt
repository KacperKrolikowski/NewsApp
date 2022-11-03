package com.krolikowski.domain.entities

data class NewsEntity(
    private val title: String,
    private val author: String,
    private val content: String,
    private val description: String,
    private val webUrl: String,
    private val imageUrl: String
)
