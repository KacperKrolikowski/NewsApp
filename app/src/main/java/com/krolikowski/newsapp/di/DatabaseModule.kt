package com.krolikowski.newsapp.di

import android.content.Context
import android.provider.DocumentsContract.Root
import androidx.room.Room
import com.krolikowski.domain.database.NewsAppDatabase
import com.krolikowski.domain.database.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsAppDatabase(@ApplicationContext context: Context): NewsAppDatabase {
        return Room.databaseBuilder(
            context,
            NewsAppDatabase::class.java,
            NEWSAPP_DB
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(newsAppDatabase: NewsAppDatabase): NewsDao =
        newsAppDatabase.newsDao()

    companion object {
        private const val NEWSAPP_DB = "NEWSAPP_DB"
    }
}