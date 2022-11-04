package com.krolikowski.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.domain.pagination.NewsPagingSource
import com.krolikowski.domain.reposotories.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsByQueryUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    fun execute(query: String): Flow<PagingData<NewsEntity>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE
        ),
        pagingSourceFactory = {
            NewsPagingSource(
                newsRepository,
                query
            )
        }
    ).flow

    companion object {
        private const val PAGE_SIZE = 40
        private const val INITIAL_LOAD_SIZE = 1
    }
}