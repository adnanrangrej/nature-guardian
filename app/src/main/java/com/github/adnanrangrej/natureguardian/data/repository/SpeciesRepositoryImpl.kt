package com.github.adnanrangrej.natureguardian.data.repository

import com.github.adnanrangrej.natureguardian.data.local.dao.species.FullSpeciesDetailDao
import com.github.adnanrangrej.natureguardian.data.local.dao.species.SpeciesDao
import com.github.adnanrangrej.natureguardian.data.mapper.toDetailedSpecies
import com.github.adnanrangrej.natureguardian.data.mapper.toSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.Species
import com.github.adnanrangrej.natureguardian.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SpeciesRepositoryImpl(
    private val fullSpeciesDetailDao: FullSpeciesDetailDao,
    private val speciesDao: SpeciesDao
) : SpeciesRepository {
    override fun getSpeciesById(internalTaxonId: Long): Flow<DetailedSpecies?> =
        fullSpeciesDetailDao.getFullSpeciesDetailById(internalTaxonId)
            .map { it?.toDetailedSpecies() }

    override fun getAllSpecies(): Flow<List<Species>> =
        speciesDao.getAllSpecies()
            .map { list -> list.map { it.toSpecies() } }

    override fun getBookmarkedSpecies(): Flow<List<Species>> =
        speciesDao.getAllBookmarkedSpecies()
            .map { list -> list.map { it.toSpecies() } }

    override suspend fun bookmarkSpecies(
        internalTaxonId: Long,
        isBookmarked: Boolean
    ) = speciesDao.bookmarkSpecies(internalTaxonId, isBookmarked)
}