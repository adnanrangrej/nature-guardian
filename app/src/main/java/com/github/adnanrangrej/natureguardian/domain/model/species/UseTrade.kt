package com.github.adnanrangrej.natureguardian.domain.model.species

data class UseTrade(
    val useTradeId: Long,

    val speciesId: Long,

    val code: String,

    val useTradeName: String,

    val international: Boolean?,

    val national: Boolean?
)