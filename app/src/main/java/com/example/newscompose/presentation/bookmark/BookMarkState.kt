package com.example.newscompose.presentation.bookmark

import com.example.newscompose.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
