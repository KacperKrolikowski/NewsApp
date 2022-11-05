package com.krolikowski.domain.usecases

import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.domain.reposotories.SavedRepository
import javax.inject.Inject

class GetSavedNewsUseCase @Inject constructor(
    private val savedRepository: SavedRepository
) {
    suspend fun execute(): List<NewsEntity> = savedRepository.getAllSaved()
}