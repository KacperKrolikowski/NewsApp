package com.krolikowski.domain.reposotories

import com.krolikowski.domain.entities.NewsDatabaseEntity
import kotlinx.coroutines.flow.Flow

interface SavedRepository {
    suspend fun checkIsSaved(webUrl: String): Boolean
    suspend fun addToSaved(news: NewsDatabaseEntity)
    suspend fun removeFromSaved(news: NewsDatabaseEntity)
    fun getAllSaved(): Flow<List<NewsDatabaseEntity>>
}