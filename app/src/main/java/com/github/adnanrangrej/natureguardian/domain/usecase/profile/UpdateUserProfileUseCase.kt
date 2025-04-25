package com.github.adnanrangrej.natureguardian.domain.usecase.profile

import com.github.adnanrangrej.natureguardian.domain.model.profile.User
import com.github.adnanrangrej.natureguardian.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke(user: User) = profileRepository.updateUserProfile(user)
}