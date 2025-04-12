package com.github.adnanrangrej.natureguardian.domain.model.species

data class SpeciesDetail(
    val speciesId: Long,

    val description: String?,

    val conservationActionsDescription: String?,

    val habitatDescription: String?,

    val useTradeDescription: String?,

    val threatsDescription: String?,

    val populationDescription: String?
)
