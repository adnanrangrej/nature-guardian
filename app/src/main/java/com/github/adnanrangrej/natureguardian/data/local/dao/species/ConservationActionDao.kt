package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.ConservationAction

interface ConservationActionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConservationAction(conservationAction: ConservationAction): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConservationActionList(conservationActionList: List<ConservationAction>)

}