package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Species
import kotlinx.coroutines.flow.Flow

@Dao
interface SpeciesDao {

    // Basic CRUD operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecies(species: Species): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesList(speciesList: List<Species>)

    @Update
    suspend fun updateSpecies(species: Species)

    @Delete
    suspend fun deleteSpecies(species: Species)

    @Query("SELECT * FROM species WHERE internal_taxon_id = :internalTaxonId")
    fun getSpeciesById(internalTaxonId: Long): Flow<Species?>

    @Query("SELECT * FROM species")
    fun getAllSpecies(): Flow<List<Species>>

}