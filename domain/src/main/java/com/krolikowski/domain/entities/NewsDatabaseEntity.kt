package com.krolikowski.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsDatabaseEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "newsUrl") val webUrl: String,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String,
    val date: String
) {
    fun toNewsEntity() = NewsEntity(
        title = this.title,
        author = this.author,
        description = this.description,
        webUrl = this.webUrl,
        imageUrl = this.imageUrl,
        date = this.date
    )
}