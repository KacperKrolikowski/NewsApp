package com.krolikowski.domain.usecases

import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.domain.reposotories.SavedRepository
import javax.inject.Inject

class DeleteSavedNewsUseCase @Inject constructor(
    private val savedRepository: SavedRepository
) {
    suspend fun execute(newsEntity: NewsEntity) =
        savedRepository.removeFromSaved(newsEntity.toNewsDatabaseEntity())
}