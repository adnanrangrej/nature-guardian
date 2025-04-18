package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ThreatEntity

@Dao
interface ThreatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreat(threat: ThreatEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreatList(threatList: List<ThreatEntity>)
}