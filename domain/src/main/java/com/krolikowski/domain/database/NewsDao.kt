package com.krolikowski.domain.database

import androidx.room.*
import com.krolikowski.domain.entities.NewsDatabaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(newsDatabaseEntity: NewsDatabaseEntity)

    @Delete
    suspend fun deleteNews(newsDatabaseEntity: NewsDatabaseEntity)

    @Query("SELECT * from news")
    fun getSavedNews(): Flow<List<NewsDatabaseEntity>>

    @Query("SELECT * from news WHERE :webUrl = newsUrl")
    suspend fun checkIsSaved(webUrl: String): NewsDatabaseEntity?
}