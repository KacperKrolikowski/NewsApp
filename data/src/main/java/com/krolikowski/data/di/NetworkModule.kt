package com.krolikowski.data.di

import android.content.Context
import com.krolikowski.data.BuildConfig
import com.krolikowski.data.NewsAPI
import com.krolikowski.data.utils.HttpCacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpCache(@ApplicationContext context: Context) = Cache(
        directory = context.cacheDir,
        maxSize = CACHE_SIZE
    )

    @Singleton
    @Provides
    fun provideHttpCacheInterceptor() = HttpCacheInterceptor(
        cacheValidityTime = CACHE_VALIDITY_TIME_MINUTES,
        validityTimeUnit = TimeUnit.MINUTES
    )

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideDefaultOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cacheInterceptor: HttpCacheInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(cacheInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideGsonConverterFactory() = GsonConverterFactory
        .create()

    @Named(NEWS_API)
    @Singleton
    @Provides
    fun provideConfigApiRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(NEWS_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideNewsApi(@Named(NEWS_API) retrofit: Retrofit): NewsAPI =
        retrofit.create(
            NewsAPI::class.java
        )

    companion object {
        const val NEWS_API = "NEWS_API"
        const val NEWS_API_URL = "https://newsapi.org/"
        const val CONNECTION_TIMEOUT_MS = 15000L
        const val READ_TIMEOUT_MS = 15000L

        const val CACHE_SIZE = 30 * 1024 * 1024L
        const val CACHE_VALIDITY_TIME_MINUTES = 60
    }
}