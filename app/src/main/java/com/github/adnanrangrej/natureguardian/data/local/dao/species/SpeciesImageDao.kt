package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesImage

interface SpeciesImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesImage(speciesImage: SpeciesImage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesImageList(speciesImageList: List<SpeciesImage>)

}