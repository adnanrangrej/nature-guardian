package com.github.adnanrangrej.natureguardian.di

import com.github.adnanrangrej.natureguardian.data.local.dao.species.FullSpeciesDetailDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.SpeciesDao
import com.github.adnanrangrej.natureguardian.data.remote.api.chatbot.ChatBotApiService
import com.github.adnanrangrej.natureguardian.data.remote.api.news.NewsApiService
import com.github.adnanrangrej.natureguardian.data.repository.AuthRepositoryImpl
import com.github.adnanrangrej.natureguardian.data.repository.ChatBotRepositoryImpl
import com.github.adnanrangrej.natureguardian.data.repository.NewsRepositoryImpl
import com.github.adnanrangrej.natureguardian.data.repository.ProfileRepositoryImpl
import com.github.adnanrangrej.natureguardian.data.repository.SpeciesRepositoryImpl
import com.github.adnanrangrej.natureguardian.domain.repository.AuthRepository
import com.github.adnanrangrej.natureguardian.domain.repository.ChatBotRepository
import com.github.adnanrangrej.natureguardian.domain.repository.NewsRepository
import com.github.adnanrangrej.natureguardian.domain.repository.ProfileRepository
import com.github.adnanrangrej.natureguardian.domain.repository.SpeciesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    @Singleton
    fun provideSpeciesRepository(
        fullSpeciesDetailDao: FullSpeciesDetailDao,
        speciesDao: SpeciesDao
    ): SpeciesRepository {
        return SpeciesRepositoryImpl(fullSpeciesDetailDao, speciesDao)
    }

    @Provides
    @Singleton
    fun provideChatBotRepository(@Named("chatbot") chatBotApiService: ChatBotApiService): ChatBotRepository {
        return ChatBotRepositoryImpl(chatBotApiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): ProfileRepository {
        return ProfileRepositoryImpl(firestore, auth)
    }
}