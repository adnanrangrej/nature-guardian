package com.github.adnanrangrej.natureguardian.domain.repository

import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import kotlinx.coroutines.flow.Flow

interface SpeciesRepository {

    fun getSpeciesById(internalTaxonId: Long): Flow<DetailedSpecies?>

    fun getAllSpecies(): Flow<List<DetailedSpecies>>

    fun getBookmarkedSpecies(): Flow<List<DetailedSpecies>>

    suspend fun bookmarkSpecies(internalTaxonId: Long, isBookmarked: Boolean)
}