package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.UseTrade

@Dao
interface UseTradeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUseTrade(useTrade: UseTrade): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUseTradeList(useTradeList: List<UseTrade>)

}