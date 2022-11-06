package com.krolikowski.domain.usecases

import com.krolikowski.domain.entities.NewsDatabaseEntity
import com.krolikowski.domain.reposotories.SavedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedNewsUseCase @Inject constructor(
    private val savedRepository: SavedRepository
) {
    fun execute(): Flow<List<NewsDatabaseEntity>> = savedRepository.getAllSaved()
}