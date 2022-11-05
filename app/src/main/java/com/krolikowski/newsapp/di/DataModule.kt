package com.krolikowski.newsapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.krolikowski.data.NewsAPI
import com.krolikowski.data.repositories.NewsRepositoryImpl
import com.krolikowski.data.repositories.SavedRepositoryImpl
import com.krolikowski.data.repositories.SharedPreferenceRepositoryImpl
import com.krolikowski.domain.database.NewsDao
import com.krolikowski.domain.reposotories.NewsRepository
import com.krolikowski.domain.reposotories.SavedRepository
import com.krolikowski.domain.reposotories.SharedPreferenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferenceRepository =
        SharedPreferenceRepositoryImpl(context)

    @Singleton
    @Provides
    fun provideNewsRepository(
        api: NewsAPI,
        sharedPreferenceRepository: SharedPreferenceRepository
    ): NewsRepository =
        NewsRepositoryImpl(api, sharedPreferenceRepository)

    @Singleton
    @Provides
    fun provideSavedRepository(
        newsDao: NewsDao
    ): SavedRepository = SavedRepositoryImpl(newsDao)

}