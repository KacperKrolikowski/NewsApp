package com.krolikowski.domain.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krolikowski.domain.entities.NewsDatabaseEntity
import com.krolikowski.domain.entities.NewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(newsDatabaseEntity: NewsDatabaseEntity)

    @Delete
    suspend fun deleteNews(newsDatabaseEntity: NewsDatabaseEntity)

    @Query("SELECT * from news")
    suspend fun getSavedNews(): List<NewsDatabaseEntity>
}