package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {

    // Basic CRUD operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: SpeciesEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesList(speciesList: List<SpeciesEntity>)

    @Update
    suspend fun updateSpecies(species: SpeciesEntity)

    @Delete
    suspend fun deleteSpecies(species: SpeciesEntity)

    @Query("SELECT * FROM species WHERE internal_taxon_id = :internalTaxonId")
    fun getSpeciesById(internalTaxonId: Long): Flow<SpeciesEntity?>

    @Query("SELECT * FROM species")
    fun getAllSpecies(): Flow<List<SpeciesEntity>>

}