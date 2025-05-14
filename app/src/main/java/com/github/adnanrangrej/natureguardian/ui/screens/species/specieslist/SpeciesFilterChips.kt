package com.github.adnanrangrej.natureguardian.ui.screens.species.specieslist

enum class SpeciesFilterChips(val displayName: String, val classNames: List<String>) {
    Mammal("Mammals", listOf("MAMMALIA")),
    Bird("Birds", listOf("AVES")),
    Amphibian("Amphibians", listOf("AMPHIBIA")),
    Reptile("Reptiles", listOf("REPTILIA")),
    Plants("Plants", listOf("MAGNOLIOPSIDA", "LILIOPSIDA"))
}