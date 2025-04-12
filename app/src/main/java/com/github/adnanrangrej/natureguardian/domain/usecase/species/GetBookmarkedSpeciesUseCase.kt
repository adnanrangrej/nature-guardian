package com.github.adnanrangrej.natureguardian.domain.usecase.species

import com.github.adnanrangrej.natureguardian.domain.repository.SpeciesRepository
import javax.inject.Inject

class GetBookmarkedSpeciesUseCase @Inject constructor(private val speciesRepository: SpeciesRepository) {
    operator fun invoke() = speciesRepository.getBookmarkedSpecies()
}