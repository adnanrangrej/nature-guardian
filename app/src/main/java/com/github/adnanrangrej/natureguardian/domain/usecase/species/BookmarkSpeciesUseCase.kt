package com.github.adnanrangrej.natureguardian.domain.usecase.species

import com.github.adnanrangrej.natureguardian.domain.repository.SpeciesRepository
import javax.inject.Inject

class BookmarkSpeciesUseCase @Inject constructor(private val speciesRepository: SpeciesRepository) {
    suspend operator fun invoke(internalTaxonId: Long, isBookmarked: Boolean) {
        speciesRepository.bookmarkSpecies(internalTaxonId, isBookmarked)
    }
}