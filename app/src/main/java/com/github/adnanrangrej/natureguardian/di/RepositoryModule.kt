package com.github.adnanrangrej.natureguardian.di

import com.github.adnanrangrej.natureguardian.data.remote.api.news.NewsApiService
import com.github.adnanrangrej.natureguardian.data.repository.NewsRepositoryImpl
import com.github.adnanrangrej.natureguardian.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(@Named("news") newsApiService: NewsApiService): NewsRepository {
        return NewsRepositoryImpl(newsApiService)
    }

}