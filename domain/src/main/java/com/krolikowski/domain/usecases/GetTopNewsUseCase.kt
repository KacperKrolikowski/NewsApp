package com.krolikowski.domain.usecases

import com.krolikowski.domain.entities.NewsListEntity
import com.krolikowski.domain.reposotories.NewsRepository
import javax.inject.Inject

class GetTopNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend fun execute(): Result<NewsListEntity> {
        return newsRepository.getTopNews()
    }
}