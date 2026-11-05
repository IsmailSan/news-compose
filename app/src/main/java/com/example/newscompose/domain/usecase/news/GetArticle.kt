package com.example.newscompose.domain.usecase.news

import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.repository.NewsRepository

class GetArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article? {
        return newsRepository.getArticle(url = url)
    }
}
