package com.github.adnanrangrej.natureguardian.data.local.entity.species

import androidx.room.Embedded
import androidx.room.Relation


data class FullSpeciesDetails(

    @Embedded
    val species: Species,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val details: SpeciesDetail?,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val commonNames: List<CommonName>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val conservationActions: List<ConservationAction>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val habitats: List<Habitat>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val locations: List<Location>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val threats: List<Threat>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val useTrade: List<UseTrade>,

    @Relation(
        parentColumn = "internal_taxon_id",
        entityColumn = "species_id"
    )
    val images: List<SpeciesImage>
)