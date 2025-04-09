package com.github.adnanrangrej.natureguardian.data.local

import androidx.room.Database
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
abstract class NatureGuardianDatabase {
}