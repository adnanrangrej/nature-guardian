package com.github.adnanrangrej.natureguardian.domain.usecase.profile

import com.github.adnanrangrej.natureguardian.domain.repository.ProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke() = profileRepository.getUserProfile()
}