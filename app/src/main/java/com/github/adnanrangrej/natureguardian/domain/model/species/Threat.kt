package com.github.adnanrangrej.natureguardian.domain.model.species

data class Threat(
    val threatId: Long,

    val speciesId: Long,

    val code: String,

    val threatName: String,

    val stressCode: String?,

    val stressName: String?,

    val severity: String?,
)
