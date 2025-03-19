package com.github.adnanrangrej.natureguardian.domain.usecase.news

import com.github.adnanrangrej.natureguardian.domain.repository.NewsRepository
import javax.inject.Inject

class GetAllNewsNextPageUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(nextToken: String) = newsRepository.getAllNewsNextPage(nextToken)
}