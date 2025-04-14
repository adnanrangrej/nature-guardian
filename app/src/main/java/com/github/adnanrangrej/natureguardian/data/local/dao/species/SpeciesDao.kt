package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesEntity

@Dao
interface SpeciesDao {

    // Basic CRUD operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: SpeciesEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesList(speciesList: List<SpeciesEntity>)

    @Update
    suspend fun updateSpecies(species: SpeciesEntity)

    @Query("UPDATE species SET is_bookmarked = :isBookmarked WHERE internal_taxon_id = :internalTaxonId")
    suspend fun bookmarkSpecies(internalTaxonId: Long, isBookmarked: Boolean)

    @Delete
    suspend fun deleteSpecies(species: SpeciesEntity)

}