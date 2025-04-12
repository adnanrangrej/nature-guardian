package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "use_trade",
    foreignKeys = [ForeignKey(
        entity = SpeciesEntity::class,
        parentColumns = ["internal_taxon_id"],
        childColumns = ["species_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("species_id")]
)
data class UseTradeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "use_trade_id")
    val useTradeId: Long = 0,

    @ColumnInfo(name = "species_id")
    val speciesId: Long,

    @ColumnInfo(name = "code")
    val code: String,

    @ColumnInfo(name = "use_trade_name")
    val useTradeName: String,

    @ColumnInfo(name = "international")
    val international: Boolean?,

    @ColumnInfo(name = "national")
    val national: Boolean?
)
