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
import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonName
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationAction
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Habitat
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Location
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Species
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesDetail
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesImage
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Threat
import com.github.adnanrangrej.natureguardian.data.local.entity.species.UseTrade

@Database(
    entities = [
        // Species entity
        Species::class,
        SpeciesDetail::class,
        CommonName::class,
        ConservationAction::class,
        Habitat::class,
        Location::class,
        Threat::class,
        UseTrade::class,
        SpeciesImage::class
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