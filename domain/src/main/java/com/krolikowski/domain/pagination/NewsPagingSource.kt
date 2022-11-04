package com.krolikowski.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.krolikowski.domain.entities.NewsEntity
import com.krolikowski.domain.reposotories.NewsRepository

class NewsPagingSource(
    private val newsRepository: NewsRepository,
    private val query: String
) : PagingSource<Int, NewsEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsEntity> {
        val pageIndex = params.key ?: NewsRepository.STARTING_PAGE_INDEX
        return try {
            val assets = newsRepository.getNewsByQuery(query, pageIndex).newsList

            val nextKey = (pageIndex.plus(1)).takeUnless { assets.isEmpty() }

            LoadResult.Page(
                data = assets,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsEntity>): Int? = null
}