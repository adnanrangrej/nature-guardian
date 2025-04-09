package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "common_name",
    foreignKeys = [ForeignKey(
        entity = Species::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CommonName(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "common_name_id")
    val commonNameId: Long = 0,

    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "common_name")
    val commonName: String,

    @ColumnInfo(name = "language")
    val language: String?,

    @ColumnInfo(name = "is_main")
    val isMain: Boolean
)

