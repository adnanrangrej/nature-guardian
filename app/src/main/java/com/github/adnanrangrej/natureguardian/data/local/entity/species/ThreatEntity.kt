package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "threat",
    foreignKeys = [ForeignKey(
        entity = SpeciesEntity::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("species_id")]
)
data class ThreatEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "threat_id")
    val threatId: Long = 0,

    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "threat_name")
    val threatName: String,

    @ColumnInfo(name = "stress_code")
    val stressCode: String?,

    @ColumnInfo(name = "stress_name")
    val stressName: String?,

    @ColumnInfo(name = "severity")
    val severity: String?,
)
