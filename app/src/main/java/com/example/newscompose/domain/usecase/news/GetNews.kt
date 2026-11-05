package com.example.newscompose.domain.usecase.news

import androidx.paging.PagingData
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }
}
