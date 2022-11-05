package com.krolikowski.domain.entities

data class NewsEntity(
     val title: String,
     val author: String,
     val description: String,
     val webUrl: String,
     val imageUrl: String,
     val date: String
) {
     fun toNewsDatabaseEntity(): NewsDatabaseEntity = NewsDatabaseEntity(
          title = this.title,
          author = this.author,
          description = this.description,
          webUrl = this.webUrl,
          imageUrl = this.imageUrl,
          date = this.date
     )
}
