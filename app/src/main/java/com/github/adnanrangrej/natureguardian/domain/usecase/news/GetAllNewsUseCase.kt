package com.github.adnanrangrej.natureguardian.domain.usecase.news

import com.github.adnanrangrej.natureguardian.domain.repository.NewsRepository
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke() = newsRepository.getAllNews()
}