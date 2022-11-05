package com.krolikowski.domain.reposotories

import com.krolikowski.domain.entities.NewsDatabaseEntity
import com.krolikowski.domain.entities.NewsEntity

interface SavedRepository {
    suspend fun checkIsSaved(webUrl: String): Boolean
    suspend fun addToSaved(news: NewsDatabaseEntity)
    suspend fun removeFromSaved(news: NewsDatabaseEntity)
    suspend fun getAllSaved(): List<NewsEntity>
}