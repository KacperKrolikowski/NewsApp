package com.krolikowski.data.repositories

import com.krolikowski.data.NewsAPI
import com.krolikowski.domain.entities.NewsListEntity
import com.krolikowski.domain.reposotories.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsAPI,
    private val sharedPreferencesRepository: SharedPreferenceRepositoryImpl
) : NewsRepository {
    override suspend fun getTopNews(): Result<NewsListEntity> {
        val topNewsResult = kotlin.runCatching {
            api.getTopNews(country = sharedPreferencesRepository.appLanguageCode)
        }
        return topNewsResult.mapCatching {
            it.toEntity()
        }
    }
}