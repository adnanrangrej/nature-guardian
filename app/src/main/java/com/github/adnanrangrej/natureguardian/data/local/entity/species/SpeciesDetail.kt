package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "species_detail",
    foreignKeys = [ForeignKey(
        entity = Species::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("species_id")]
)
data class SpeciesDetail(
    @PrimaryKey
    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "conservation_actions_description")
    val conservationActionsDescription: String?,

    @ColumnInfo(name = "habitat_description")
    val habitatDescription: String?,

    @ColumnInfo(name = "use_trade_description")
    val useTradeDescription: String?,

    @ColumnInfo(name = "threats_description")
    val threatsDescription: String?,

    @ColumnInfo(name = "population_description")
    val populationDescription: String?,

    )
