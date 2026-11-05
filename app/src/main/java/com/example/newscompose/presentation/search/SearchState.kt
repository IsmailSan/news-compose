package com.example.newscompose.presentation.search

import androidx.paging.PagingData
import com.example.newscompose.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)
