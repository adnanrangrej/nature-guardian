package com.github.adnanrangrej.natureguardian.domain.model.species

data class Habitat(
    val habitatId: Long,

    val speciesId: Long,

    val code: String,

    val habitatName: String,

    val majorImportance: Boolean?,

    val season: String?,

    val suitability: String?
)