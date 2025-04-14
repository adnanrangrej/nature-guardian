package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.github.adnanrangrej.natureguardian.data.local.entity.species.FullSpeciesDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface FullSpeciesDetailDao {

    @Transaction
    @Query("SELECT * FROM species WHERE internal_taxon_id = :internalTaxonId")
    fun getFullSpeciesDetailById(internalTaxonId: Long): Flow<FullSpeciesDetails?>

    @Transaction
    @Query("SELECT * FROM species")
    fun getAllFullSpeciesDetails(): Flow<List<FullSpeciesDetails>>

    @Transaction
    @Query("SELECT * FROM species WHERE is_bookmarked = 1")
    fun getAllBookmarkedSpecies(): Flow<List<FullSpeciesDetails>>

}