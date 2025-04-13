package com.github.adnanrangrej.natureguardian.domain.repository

import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.Species
import kotlinx.coroutines.flow.Flow

interface SpeciesRepository {

    fun getSpeciesById(internalTaxonId: Long): Flow<DetailedSpecies?>

    fun getAllSpecies(): Flow<List<Species>>

    fun getBookmarkedSpecies(): Flow<List<Species>>

    suspend fun bookmarkSpecies(internalTaxonId: Long, isBookmarked: Boolean)
}