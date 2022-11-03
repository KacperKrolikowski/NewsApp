package com.krolikowski.data

import com.krolikowski.data.responses.trendingnews.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query(QUERY_COUNTRY) country: String,
        @Query(QUERY_API_KEY) apiKey: String = QUERY_API_KEY_VALUE
    ): NewsListResponse

    companion object {
        private const val QUERY_COUNTRY = "country"
        private const val QUERY_API_KEY = "apiKey"
        private const val QUERY_API_KEY_VALUE = "14e1622c28f948fe86a6cbd24243ecc0"

        const val CACHE_CONTROL_HEADER = "Cache-Control"
        const val CACHE_CONTROL_NO_CACHE = "no-cache"
    }
}