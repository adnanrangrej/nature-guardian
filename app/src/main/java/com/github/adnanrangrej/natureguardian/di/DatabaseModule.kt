package com.github.adnanrangrej.natureguardian.di

import android.content.Context
import androidx.room.Room
import com.github.adnanrangrej.natureguardian.data.local.NatureGuardianDatabase
import com.github.adnanrangrej.natureguardian.data.local.processor.PrepopulateCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        callback: PrepopulateCallback
    ): NatureGuardianDatabase {
        return Room.databaseBuilder(
            context,
            NatureGuardianDatabase::class.java,
            "nature_guardian_database"
        )
            .fallbackToDestructiveMigration(true)
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun providePrepopulateCallback(
        @ApplicationContext context: Context,
        databaseProvider: Provider<NatureGuardianDatabase>
    ) = PrepopulateCallback(
        context = context,
        databaseProvider = databaseProvider
    )

    @Provides
    @Singleton
    fun provideCommonNameDao(database: NatureGuardianDatabase) = database.commonNameDao()

    @Provides
    @Singleton
    fun provideConservationActionDao(database: NatureGuardianDatabase) =
        database.conservationActionDao()

    @Provides
    @Singleton
    fun provideFullSpeciesDetailsDao(database: NatureGuardianDatabase) =
        database.fullSpeciesDetailDao()

    @Provides
    @Singleton
    fun provideHabitatDao(database: NatureGuardianDatabase) = database.habitatDao()

    @Provides
    @Singleton
    fun provideLocationDao(database: NatureGuardianDatabase) = database.locationDao()

    @Provides
    @Singleton
    fun provideSpeciesDao(database: NatureGuardianDatabase) = database.speciesDao()

    @Provides
    @Singleton
    fun provideSpeciesDetailDao(database: NatureGuardianDatabase) = database.speciesDetailDao()

    @Provides
    @Singleton
    fun provideSpeciesImageDao(database: NatureGuardianDatabase) = database.speciesImageDao()

    @Provides
    @Singleton
    fun provideThreatDao(database: NatureGuardianDatabase) = database.threatDao()

    @Provides
    @Singleton
    fun provideUseTradeDao(database: NatureGuardianDatabase) = database.useTradeDao()

}