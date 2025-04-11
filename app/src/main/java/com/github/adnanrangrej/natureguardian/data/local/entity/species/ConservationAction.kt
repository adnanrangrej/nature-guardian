package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "conservation_action",
    foreignKeys = [ForeignKey(
        entity = Species::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("species_id")]
)
data class ConservationAction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "conservation_action_id")
    val conservationActionId: Long,

    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "action_name")
    val actionName: String
)