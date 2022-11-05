package com.krolikowski.domain.usecases

import com.krolikowski.domain.reposotories.SavedRepository
import javax.inject.Inject

class CheckIsNewsSavedUseCase @Inject constructor(
    private val savedRepository: SavedRepository
) {
    suspend fun execute(webUrl: String): Boolean = savedRepository.checkIsSaved(webUrl)
}