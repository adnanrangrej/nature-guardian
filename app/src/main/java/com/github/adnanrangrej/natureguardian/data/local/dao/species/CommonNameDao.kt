package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonNameEntity

@Dao
interface CommonNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommonName(commonName: CommonNameEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommonNameList(commonNameList: List<CommonNameEntity>)

}