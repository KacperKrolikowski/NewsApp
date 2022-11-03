package com.krolikowski.data

import com.krolikowski.data.responses.trendingnews.TrendingNewsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Path

interface BingAPI {

    @GET("news/trendingtopics?textFormat=Raw&safeSearch=Off")
    suspend fun getTrendingNews(
        @HeaderMap HEADER: Map<String, String>
    ): TrendingNewsResponse

    companion object {
        private const val RAPID_HEADER_SDK = "X-BingApis-SDK"
        private const val RAPID_HEADER_SDK_VALUE = "true"
        private const val RAPID_HEADER_KEY = "X-RapidAPI-Key"
        private const val RAPID_HEADER_KEY_VALUE =
            "c9016797e3mshcb3f479b86fc845p1fcc9djsnd7a2f17e2f37"
        private const val RAPID_HEADER_HOST = "X-RapidAPI-Host"
        private const val RAPID_HEADER_HOST_VALUE = "bing-news-search1.p.rapidapi.com"

        private val HEADERS = mapOf(
            RAPID_HEADER_SDK to RAPID_HEADER_SDK_VALUE,
            RAPID_HEADER_KEY to RAPID_HEADER_KEY_VALUE,
            RAPID_HEADER_HOST to RAPID_HEADER_HOST_VALUE
        )
    }
}