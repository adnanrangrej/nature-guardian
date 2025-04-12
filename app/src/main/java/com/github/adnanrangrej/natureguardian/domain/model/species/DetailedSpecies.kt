package com.github.adnanrangrej.natureguardian.domain.model.species

data class DetailedSpecies(
    val species: Species,

    val details: SpeciesDetail?,

    val commonNames: List<CommonName>,

    val conservationActions: List<ConservationAction>,

    val habitats: List<Habitat>,

    val locations: List<Location>,

    val threats: List<Threat>,

    val useTrade: List<UseTrade>,

    val images: List<SpeciesImage>
)
