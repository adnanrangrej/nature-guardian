package com.github.adnanrangrej.natureguardian.domain.usecase.profile

import com.github.adnanrangrej.natureguardian.domain.repository.ProfileRepository
import java.io.File
import javax.inject.Inject

class UploadProfileImageUseCase @Inject constructor(private val profileRepository: ProfileRepository) {
    suspend operator fun invoke(file: File) = profileRepository.uploadProfileImage(file)

}