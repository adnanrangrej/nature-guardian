package com.github.adnanrangrej.natureguardian.domain.model.species

data class ConservationAction(
    val conservationActionId: Long,

    val speciesId: Long,

    val code: String,

    val actionName: String
)
