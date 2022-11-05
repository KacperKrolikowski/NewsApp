package com.krolikowski.data.repositories

import com.krolikowski.domain.database.NewsDao
import com.krolikowski.domain.entities.NewsDatabaseEntity
import com.krolikowski.domain.reposotories.SavedRepository

class SavedRepositoryImpl(
    private val newsDao: NewsDao
) : SavedRepository {
    override suspend fun checkIsSaved(webUrl: String) = newsDao.checkIsSaved(webUrl) != null

    override suspend fun addToSaved(news: NewsDatabaseEntity) {
        newsDao.addNews(news)
    }

    override suspend fun removeFromSaved(news: NewsDatabaseEntity) {
        newsDao.deleteNews(news)
    }

    override suspend fun getAllSaved() = newsDao.getSavedNews().map {
        it.toNewsEntity()
    }
}