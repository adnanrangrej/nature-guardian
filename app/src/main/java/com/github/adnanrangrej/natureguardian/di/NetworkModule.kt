package com.github.adnanrangrej.natureguardian.di

import com.github.adnanrangrej.natureguardian.Utils.getBackendBaseUrl
import com.github.adnanrangrej.natureguardian.data.remote.api.chatbot.ChatBotApiService
import com.github.adnanrangrej.natureguardian.data.remote.api.news.NewsApiService
import com.github.adnanrangrej.natureguardian.data.remote.api.notification.BackendApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("backend")
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBackendBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("backend")
    fun provideBackendApiService(@Named("backend") retrofit: Retrofit): BackendApiService {
        return retrofit.create(BackendApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("news")
    fun provideNewsApiService(@Named("backend") retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("chatbot")
    fun provideChatBotApiService(@Named("backend") retrofit: Retrofit): ChatBotApiService {
        return retrofit.create(ChatBotApiService::class.java)
    }
}