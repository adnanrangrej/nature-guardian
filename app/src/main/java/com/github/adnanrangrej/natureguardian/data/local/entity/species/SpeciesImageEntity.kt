package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "species_image",
    foreignKeys = [ForeignKey(
        entity = SpeciesEntity::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("species_id")]
)
data class SpeciesImageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "image_id")
    val imageId: Long = 0,

    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "image_url")
    val imageUrl: String
)
