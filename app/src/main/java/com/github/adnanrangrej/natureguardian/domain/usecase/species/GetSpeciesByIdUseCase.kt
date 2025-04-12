package com.github.adnanrangrej.natureguardian.domain.usecase.species

import com.github.adnanrangrej.natureguardian.domain.repository.SpeciesRepository
import javax.inject.Inject

class GetSpeciesByIdUseCase @Inject constructor(private val speciesRepository: SpeciesRepository) {
    operator fun invoke(internalTaxonId: Long) = speciesRepository.getSpeciesById(internalTaxonId)
}