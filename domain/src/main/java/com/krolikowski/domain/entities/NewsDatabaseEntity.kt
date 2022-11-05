package com.krolikowski.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsDatabaseEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "newsUrl")
    val imageUrl: String,
    val title: String,
    val author: String,
    val description: String,
    val webUrl: String,
    val date: String
)