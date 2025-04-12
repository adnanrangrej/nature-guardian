package com.github.adnanrangrej.natureguardian.data.repository

import com.github.adnanrangrej.natureguardian.data.local.dao.species.FullSpeciesDetailDao
import com.github.adnanrangrej.natureguardian.data.mapper.toDetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.model.species.DetailedSpecies
import com.github.adnanrangrej.natureguardian.domain.repository.SpeciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SpeciesRepositoryImpl(
    private val fullSpeciesDetailDao: FullSpeciesDetailDao
) : SpeciesRepository {
    override fun getSpeciesById(internalTaxonId: Long): Flow<DetailedSpecies?> =
        fullSpeciesDetailDao.getFullSpeciesDetailById(internalTaxonId)
            .map { it?.toDetailedSpecies() }

    override fun getAllSpecies(): Flow<List<DetailedSpecies>> =
        fullSpeciesDetailDao.getAllFullSpeciesDetails()
            .map { list -> list.map { it.toDetailedSpecies() } }

    override fun getBookmarkedSpecies(): Flow<List<DetailedSpecies>> =
        fullSpeciesDetailDao.getBookmarkedFullSpeciesDetails()
            .map { list -> list.map { it.toDetailedSpecies() } }

    override suspend fun bookmarkSpecies(
        internalTaxonId: Long,
        isBookmarked: Boolean
    ) = fullSpeciesDetailDao.bookmarkSpecies(internalTaxonId, isBookmarked)
}