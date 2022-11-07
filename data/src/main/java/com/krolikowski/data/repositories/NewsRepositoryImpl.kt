package com.krolikowski.data.repositories

import com.krolikowski.data.NewsAPI
import com.krolikowski.domain.entities.NewsListEntity
import com.krolikowski.domain.reposotories.NewsRepository
import com.krolikowski.domain.reposotories.SharedPreferenceRepository

class NewsRepositoryImpl(
    private val api: NewsAPI,
    private val sharedPreferencesRepository: SharedPreferenceRepository
) : NewsRepository {
    override suspend fun getTopNews(): Result<NewsListEntity> {
        val topNewsResult = kotlin.runCatching {
            api.getTopNews(country = sharedPreferencesRepository.topNewsCountryCode)
        }
        return topNewsResult.mapCatching {
            it.toEntity()
        }
    }

    override suspend fun getNewsByQuery(query: String, pageIndex: Int): NewsListEntity {
        val topNewsResult = api.getNewsByQuery(
            pageNumber = pageIndex,
            query = query,
            language = sharedPreferencesRepository.newsLanguageCode
        )

        return topNewsResult.toEntity()
    }
}