package com.krolikowski.domain.entities

import androidx.room.Entity

data class NewsEntity(
     val title: String,
     val author: String,
     val description: String,
     val webUrl: String,
     val imageUrl: String,
     val date: String
)
