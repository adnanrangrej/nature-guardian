package com.github.adnanrangrej.natureguardian.data.local.dao.species

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.github.adnanrangrej.natureguardian.data.local.entity.species.CommonName

interface CommonNameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommonName(commonName: CommonName): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommonNameList(commonNameList: List<CommonName>)

}