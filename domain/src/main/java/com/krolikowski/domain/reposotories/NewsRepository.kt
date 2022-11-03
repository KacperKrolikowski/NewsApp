package com.krolikowski.domain.reposotories

import com.krolikowski.domain.entities.NewsListEntity

interface NewsRepository {
    suspend fun getTopNews(): Result<NewsListEntity>
}