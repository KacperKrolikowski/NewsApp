package com.krolikowski.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krolikowski.domain.entities.NewsDatabaseEntity
import com.krolikowski.domain.entities.NewsEntity

@Database(
    entities = [NewsDatabaseEntity::class],
    version = 1,
    exportSchema = false
)

abstract class NewsAppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}