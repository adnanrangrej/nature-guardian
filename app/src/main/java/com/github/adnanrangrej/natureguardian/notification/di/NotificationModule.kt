package com.github.adnanrangrej.natureguardian.notification.di

import android.content.Context
import com.github.adnanrangrej.natureguardian.notification.network.BackendApiService
import com.github.adnanrangrej.natureguardian.notification.repository.NotificationRepository
import com.github.adnanrangrej.natureguardian.notification.repository.NotificationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    private const val CHANNEL_ID = "conservation_news_channel"
    private const val CHANNEL_NAME = "Conservation News"
    private const val CHANNEL_DESCRIPTION = "Channel for Conservation News"

    @Provides
    @Singleton
    fun provideNotificationRepository(
        @ApplicationContext context: Context,
        @Named("backend") apiService: BackendApiService
    ): NotificationRepository {
        return NotificationRepositoryImpl(
            context = context,
            channelId = CHANNEL_ID,
            channelName = CHANNEL_NAME,
            channelDescription = CHANNEL_DESCRIPTION,
            apiService = apiService
        )
    }

    @Provides
    @Singleton
    fun provideChannelId() = CHANNEL_ID

    @Provides
    @Singleton
    fun provideChannelName() = CHANNEL_NAME

    @Provides
    @Singleton
    fun provideChannelDescription() = CHANNEL_DESCRIPTION
}