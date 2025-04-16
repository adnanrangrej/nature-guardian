package com.github.adnanrangrej.natureguardian.data.local.preferences

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.room.withTransaction
import com.github.adnanrangrej.natureguardian.data.local.NatureGuardianDatabase
import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonNameEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationActionEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.HabitatEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.LocationEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesDetailEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesImageEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ThreatEntity
import com.github.adnanrangrej.natureguardian.data.local.entity.species.UseTradeEntity
import com.github.adnanrangrej.natureguardian.data.local.processor.parseCsvFile
import javax.inject.Inject

class PrefsHelper @Inject constructor(
    private val context: Context,
    private val database: NatureGuardianDatabase

) {
    private val prefs = context.getSharedPreferences("NatureGuardianPrefs", Context.MODE_PRIVATE)

    fun setPrepopulatedDatabase(value: Boolean) {
        prefs.edit { putBoolean("isPopulated", value) }
    }

    fun checkIfPrepopulatedDatabase(): Boolean {
        return prefs.getBoolean("isPopulated", false)
    }

    suspend fun checkAndPrepopulateDatabase() {
        if (!checkIfPrepopulatedDatabase()) {
            Log.d("PrefsHelper", "Pre-populating database...")
            prePopulateData()
        }
    }

    private suspend fun prePopulateData() {
        try {
            val commonNameDao = database.commonNameDao()
            val conservationActionDao = database.conservationActionDao()
            val habitatDao = database.habitatDao()
            val locationDao = database.locationDao()
            val speciesDao = database.speciesDao()
            val speciesDetailDao = database.speciesDetailDao()
            val speciesImageDao = database.speciesImageDao()
            val threatDao = database.threatDao()
            val useTradeDao = database.useTradeDao()

            database.withTransaction {
                // Parse and insert species data
                Log.d("PrefsHelper", "Starting to parse and insert species data.....")
                parseAndInsert("species.csv", true, speciesDao::insertSpeciesList) { row ->
                    SpeciesEntity(
                        internalTaxonId = row[0].toLong(),
                        scientificName = row[1],
                        redlistCategory = row[2],
                        redlistCriteria = row[3],
                        kingdomName = row[4],
                        phylumName = row[5],
                        className = row[6],
                        orderName = row[7],
                        familyName = row[8],
                        genusName = row[9],
                        speciesEpithet = row[10],
                        doi = row[11],
                        populationTrend = row[12],
                        hasImage = row[13].toBoolean()
                    )
                }

                // Parse and insert species detail data
                Log.d(
                    "PrefsHelper",
                    "Starting to parse and insert species detail data....."
                )
                parseAndInsert(
                    "species_detail.csv",
                    true,
                    speciesDetailDao::insertSpeciesDetailList
                ) { row ->
                    SpeciesDetailEntity(
                        speciesId = row[0].toLong(),
                        description = row[1],
                        conservationActionsDescription = row[2],
                        habitatDescription = row[3],
                        useTradeDescription = row[4],
                        threatsDescription = row[5],
                        populationDescription = row[6]
                    )
                }

                // Parse and insert common name data
                Log.d(
                    "PrefsHelper",
                    "Starting to parse and insert common name data....."
                )
                parseAndInsert(
                    "common_name.csv",
                    true,
                    commonNameDao::insertCommonNameList
                ) { row ->
                    CommonNameEntity(
                        speciesId = row[0].toLong(),
                        commonName = row[1],
                        language = row[2],
                        isMain = row[3].toBoolean()
                    )
                }

                // Parse and insert conservation action data
                Log.d(
                    "PrefsHelper",
                    "Starting to parse and insert conservation action data....."
                )
                parseAndInsert(
                    "conservation_action.csv",
                    true,
                    conservationActionDao::insertConservationActionList
                ) { row ->
                    ConservationActionEntity(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        actionName = row[2]
                    )
                }

                // Parse and insert habitat data
                Log.d("PrefsHelper", "Starting to parse and insert habitat data.....")
                parseAndInsert("habitat.csv", true, habitatDao::insertHabitatList) { row ->
                    val majorImportance = row.getOrNull(3)?.let {
                        it.isNotBlank() && it.lowercase() == "yes"
                    }
                    HabitatEntity(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        habitatName = row[2],
                        majorImportance = majorImportance,
                        season = row[4],
                        suitability = row[5]
                    )
                }

                // Parse and insert location data
                Log.d("PrefsHelper", "Starting to parse and insert location data.....")
                parseAndInsert(
                    "species_location.csv",
                    true,
                    locationDao::insertLocationList
                ) { row ->
                    LocationEntity(
                        speciesId = row[0].toLong(),
                        latitude = row[1].toDouble(),
                        longitude = row[2].toDouble()
                    )
                }

                // Parse and insert threat data
                Log.d("PrefsHelper", "Starting to parse and insert threat data.....")
                parseAndInsert("threat.csv", true, threatDao::insertThreatList) { row ->
                    ThreatEntity(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        threatName = row[2],
                        stressCode = row[3],
                        stressName = row[4],
                        severity = row[5]
                    )
                }

                // Parse and insert use trade data
                Log.d("PrefsHelper", "Starting to parse and insert use trade data.....")
                parseAndInsert("use_trade.csv", true, useTradeDao::insertUseTradeList) { row ->
                    UseTradeEntity(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        useTradeName = row[2],
                        international = row[3].toBoolean(),
                        national = row[4].toBoolean(),
                    )
                }

                // Parse and insert species image data
                Log.d(
                    "PrefsHelper",
                    "Starting to parse and insert species image data....."
                )
                parseAndInsert(
                    "species_image.csv",
                    true,
                    speciesImageDao::insertSpeciesImageList
                ) { row ->
                    SpeciesImageEntity(
                        speciesId = row[0].toLong(),
                        imageUrl = row[1]
                    )
                }
                setPrepopulatedDatabase(true)
                Log.d("PrefsHelper", "Database pre-populated successfully.")
            }
        } catch (e: Exception) {
            Log.e("PrefsHelper", "Error pre-populating database", e)
            throw e
        }
    }

    private suspend fun <T> parseAndInsert(
        filePath: String,
        skipHeader: Boolean = true,
        insertFunction: suspend (List<T>) -> Unit,
        mapRow: (Array<String>) -> T,
    ) {
        val results: List<T> = parseCsvFile(filePath, skipHeader, context, mapRow)
        try {
            insertFunction(results)
            Log.d("PrefsHelper", "Data in $filePath inserted successfully")
        } catch (e: Exception) {
            Log.e("PrefsHelper", "Error inserting data", e)
            throw e
        }
    }
}

