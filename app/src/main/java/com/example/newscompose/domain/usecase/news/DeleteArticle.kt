package com.example.newscompose.domain.usecase.news

import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article = article)
    }
}
