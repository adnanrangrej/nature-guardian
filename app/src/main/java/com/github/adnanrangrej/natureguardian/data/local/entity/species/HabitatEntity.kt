package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "habitat",
    foreignKeys = [ForeignKey(
        entity = SpeciesEntity::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("species_id")]
)
data class HabitatEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habitat_id")
    val habitatId: Long = 0,

    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "habitat_name")
    val habitatName: String,

    @ColumnInfo(name = "major_importance")
    val majorImportance: Boolean?,

    @ColumnInfo(name = "season")
    val season: String?,

    @ColumnInfo(name = "suitability")
    val suitability: String?
)
