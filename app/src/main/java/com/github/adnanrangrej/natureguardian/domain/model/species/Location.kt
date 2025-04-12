package com.github.adnanrangrej.natureguardian.domain.model.species

data class Location(
    val locationId: Long,

    val speciesId: Long,

    val latitude: Double,

    val longitude: Double,
)
