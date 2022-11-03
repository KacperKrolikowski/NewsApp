package com.krolikowski.data.utils

import com.krolikowski.data.NewsAPI.Companion.CACHE_CONTROL_HEADER
import com.krolikowski.data.NewsAPI.Companion.CACHE_CONTROL_NO_CACHE
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class HttpCacheInterceptor(
    private val cacheValidityTime: Int,
    private val validityTimeUnit: TimeUnit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE

        val originalResponse = chain.proceed(request)

        return if (shouldUseCache) {
            originalResponse.getCachedResponse()
        } else {
            originalResponse
        }
    }

    private fun Response.getCachedResponse(): Response {
        val cacheControl = CacheControl.Builder()
            .maxAge(cacheValidityTime, validityTimeUnit)
            .build()

        return this.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}