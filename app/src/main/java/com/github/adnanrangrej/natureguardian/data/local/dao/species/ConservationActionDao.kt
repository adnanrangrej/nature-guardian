package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationActionEntity

@Dao
interface ConservationActionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConservationAction(conservationAction: ConservationActionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConservationActionList(conservationActionList: List<ConservationActionEntity>)

}