package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.Embedded
import androidx.room.Relation


data class FullSpeciesDetails(

    @Embedded
    val species: SpeciesEntity,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val details: SpeciesDetailEntity?,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val commonNames: List<CommonNameEntity>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val conservationActions: List<ConservationActionEntity>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val habitats: List<HabitatEntity>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val locations: List<LocationEntity>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val threats: List<ThreatEntity>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val useTrade: List<UseTradeEntity>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val images: List<SpeciesImageEntity>
)