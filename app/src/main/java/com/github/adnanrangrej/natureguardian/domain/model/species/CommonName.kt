package com.github.adnanrangrej.natureguardian.domain.model.species

data class CommonName(
    val commonNameId: Long,

    val speciesId: Long,

    val commonName: String,

    val language: String?,

    val isMain: Boolean
)