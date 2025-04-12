package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.HabitatEntity

@Dao
interface HabitatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitat(habitat: HabitatEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitatList(habitatList: List<HabitatEntity>)

}