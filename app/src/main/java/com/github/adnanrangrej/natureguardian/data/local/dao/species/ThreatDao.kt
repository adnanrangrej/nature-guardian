package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.Threat

interface ThreatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreat(threat: Threat): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreatList(threatList: List<Threat>)
}