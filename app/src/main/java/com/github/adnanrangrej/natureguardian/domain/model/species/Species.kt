package com.github.adnanrangrej.natureguardian.domain.model.species

data class Species(
    val internalTaxonId: Long,

    val scientificName: String,

    val redlistCategory: String,

    val redlistCriteria: String,

    val kingdomName: String,

    val phylumName: String,

    val className: String,

    val orderName: String,

    val familyName: String,

    val genusName: String,

    val speciesEpithet: String,

    val doi: String,

    val populationTrend: String,

    val hasImage: Boolean,

    val isBookmarked: Boolean = false,
)

