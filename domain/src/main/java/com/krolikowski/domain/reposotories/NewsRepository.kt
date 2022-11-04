package com.krolikowski.domain.reposotories

import com.krolikowski.domain.entities.NewsListEntity

interface NewsRepository {
    suspend fun getTopNews(): Result<NewsListEntity>
    suspend fun getNewsByQuery(query: String, pageIndex: Int): NewsListEntity

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }
}