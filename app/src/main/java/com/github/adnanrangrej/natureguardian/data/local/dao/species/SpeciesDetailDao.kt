package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.SpeciesDetail

interface SpeciesDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesDetail(speciesDetail: SpeciesDetail): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpeciesDetailList(speciesDetailList: List<SpeciesDetail>)

}