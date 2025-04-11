package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Habitat

interface HabitatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitat(habitat: Habitat): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitatList(habitatList: List<Habitat>)

}