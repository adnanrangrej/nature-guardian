package com.github.adnanrangrej.natureguardian.data.model.species

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "species",
    indices = [Index(value = ["scientific_name"], unique = true)]
)
data class Species(
    @PrimaryKey
    @ColumnInfo(name = "internal_taxon_id")
    val internalTaxonId: Long,

    @ColumnInfo(name = "scientific_name")
    val scientificName: String,

    @ColumnInfo(name = "redlist_category")
    val redlistCategory: String,

    @ColumnInfo(name = "redlist_criteria")
    val redlistCriteria: String,

    @ColumnInfo(name = "kingdom_name")
    val kingdomName: String,

    @ColumnInfo(name = "phylum_name")
    val phylumName: String,

    @ColumnInfo(name = "class_name")
    val className: String,

    @ColumnInfo(name = "order_name")
    val orderName: String,

    @ColumnInfo(name = "family_name")
    val familyName: String,

    @ColumnInfo(name = "genus_name")
    val genusName: String,

    @ColumnInfo(name = "species_epithet")
    val speciesEpithet: String,

    @ColumnInfo(name = "doi")
    val doi: String,

    @ColumnInfo(name = "population_trend")
    val populationTrend: String,

    @ColumnInfo(name = "has_image")
    val hasImage: Boolean,
)
