package com.github.adnanrangrej.natureguardian.data.local.processor

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.room.withTransaction
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.adnanrangrej.natureguardian.data.local.NatureGuardianDatabase
import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonName
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationAction
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Habitat
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Location
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Species
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesDetail
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesImage
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Threat
import com.github.adnanrangrej.natureguardian.data.local.entity.species.UseTrade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PrepopulateCallback @Inject constructor(
    private val context: Context,
    private val databaseProvider: Provider<NatureGuardianDatabase>
) : RoomDatabase.Callback() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d(
            "PrepopulateCallback",
            "Database onCreate triggered. Starting pre-population with OpenCSV."
        )

        applicationScope.launch {
            prePopulateData()
        }
    }

    private suspend fun prePopulateData() {
        try {
            val database = databaseProvider.get()
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
                Log.d("PrepopulateCallback", "Starting to parse and insert species data.....")
                parseAndInsert("species.csv", true, speciesDao::insertSpeciesList) { row ->
                    Species(
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
                    "PrepopulateCallback",
                    "Starting to parse and insert species detail data....."
                )
                parseAndInsert(
                    "species_detail.csv",
                    true,
                    speciesDetailDao::insertSpeciesDetailList
                ) { row ->
                    SpeciesDetail(
                        speciesId = row[0].toLong(),
                        description = row[1].parseFromHtml(),
                        conservationActionsDescription = row[2].parseFromHtml(),
                        habitatDescription = row[3].parseFromHtml(),
                        useTradeDescription = row[4].parseFromHtml(),
                        threatsDescription = row[5].parseFromHtml(),
                        populationDescription = row[6].parseFromHtml()
                    )
                }

                // Parse and insert common name data
                Log.d("PrepopulateCallback", "Starting to parse and insert common name data.....")
                parseAndInsert(
                    "common_name.csv",
                    true,
                    commonNameDao::insertCommonNameList
                ) { row ->
                    CommonName(
                        speciesId = row[0].toLong(),
                        commonName = row[2],
                        language = row[3],
                        isMain = row[4].toBoolean()
                    )
                }

                // Parse and insert conservation action data
                Log.d(
                    "PrepopulateCallback",
                    "Starting to parse and insert conservation action data....."
                )
                parseAndInsert(
                    "conservation_action.csv",
                    true,
                    conservationActionDao::insertConservationActionList
                ) { row ->
                    ConservationAction(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        actionName = row[2]
                    )
                }

                // Parse and insert habitat data
                Log.d("PrepopulateCallback", "Starting to parse and insert habitat data.....")
                parseAndInsert("habitat.csv", true, habitatDao::insertHabitatList) { row ->
                    val majorImportance = row.getOrNull(3)?.let {
                        it.isNotBlank() && it.lowercase() == "yes"
                    }
                    Habitat(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        habitatName = row[2],
                        majorImportance = majorImportance,
                        season = row[4],
                        suitability = row[5]
                    )
                }

                // Parse and insert location data
                Log.d("PrepopulateCallback", "Starting to parse and insert location data.....")
                parseAndInsert("location.csv", true, locationDao::insertLocationList) { row ->
                    Location(
                        speciesId = row[0].toLong(),
                        latitude = row[1].toDouble(),
                        longitude = row[2].toDouble()
                    )
                }

                // Parse and insert threat data
                Log.d("PrepopulateCallback", "Starting to parse and insert threat data.....")
                parseAndInsert("threat.csv", true, threatDao::insertThreatList) { row ->
                    Threat(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        threatName = row[2],
                        stressCode = row[3],
                        stressName = row[4],
                        severity = row[5]
                    )
                }

                // Parse and insert use trade data
                Log.d("PrepopulateCallback", "Starting to parse and insert use trade data.....")
                parseAndInsert("use_trade.csv", true, useTradeDao::insertUseTradeList) { row ->
                    UseTrade(
                        speciesId = row[0].toLong(),
                        code = row[1],
                        useTradeName = row[2],
                        international = row[3].toBoolean(),
                        national = row[4].toBoolean(),
                    )
                }

                // Parse and insert species image data
                Log.d("PrepopulateCallback", "Starting to parse and insert species image data.....")
                parseAndInsert(
                    "species_image.csv",
                    true,
                    speciesImageDao::insertSpeciesImageList
                ) { row ->
                    SpeciesImage(
                        speciesId = row[0].toLong(),
                        imageUrl = row[1]
                    )
                }
            }

        } catch (e: Exception) {
            Log.e("PrepopulateCallback", "Error pre-populating database", e)
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
            Log.d("PrepopulateCallback", "Data in $filePath inserted successfully")
        } catch (e: Exception) {
            Log.e("PrepopulateCallback", "Error inserting data", e)
            throw e
        }
    }
}