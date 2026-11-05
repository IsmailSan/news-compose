package com.example.newscompose.presentation.home

data class HomeState(
    val newsTicker: String = "",
    val isLoading: Boolean = false,
)
