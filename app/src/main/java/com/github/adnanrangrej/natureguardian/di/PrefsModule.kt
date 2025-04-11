package com.github.adnanrangrej.natureguardian.di

import android.content.Context
import com.github.adnanrangrej.natureguardian.data.local.NatureGuardianDatabase
import com.github.adnanrangrej.natureguardian.data.local.preferences.PrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {
    @Provides
    @Singleton
    fun providePrefsHelper(
        @ApplicationContext context: Context,
        database: NatureGuardianDatabase
    ): PrefsHelper {
        return PrefsHelper(context, database)
    }
}