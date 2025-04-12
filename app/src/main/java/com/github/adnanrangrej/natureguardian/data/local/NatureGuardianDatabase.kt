package com.github.adnanrangrej.natureguardian.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.adnanrangrej.natureguardian.data.local.dao.species.CommonNameDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.ConservationActionDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.FullSpeciesDetailDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.HabitatDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.LocationDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.SpeciesDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.SpeciesDetailDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.SpeciesImageDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.ThreatDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.UseTradeDao
import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonNameEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationActionEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.HabitatEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.LocationEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesDetailEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesImageEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ThreatEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.UseTradeEntity

@Database(
    entities = [
        // Species entity
        SpeciesEntity::class,
        SpeciesDetailEntity::class,
        CommonNameEntity::class,
        ConservationActionEntity::class,
        HabitatEntity::class,
        LocationEntity::class,
        ThreatEntity::class,
        UseTradeEntity::class,
        SpeciesImageEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class NatureGuardianDatabase : RoomDatabase() {
    abstract fun speciesDao(): SpeciesDao
    abstract fun commonNameDao(): CommonNameDao
    abstract fun speciesDetailDao(): SpeciesDetailDao
    abstract fun conservationActionDao(): ConservationActionDao
    abstract fun habitatDao(): HabitatDao
    abstract fun locationDao(): LocationDao
    abstract fun threatDao(): ThreatDao
    abstract fun useTradeDao(): UseTradeDao
    abstract fun speciesImageDao(): SpeciesImageDao
    abstract fun fullSpeciesDetailDao(): FullSpeciesDetailDao
}