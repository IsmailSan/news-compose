package com.example.newscompose.domain.usecase.news

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.repository.NewsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class SearchNewsTest {

    private val newsRepository: NewsRepository = mockk()
    private lateinit var searchNews: SearchNews

    @Before
    fun setUp() {
        searchNews = SearchNews(newsRepository)
    }

    @Test
    fun `searchNews calls newsRepository searchNews with query and sources`() = runTest {
        val searchQuery = "bitcoin"
        val sources = listOf("bbc-news", "cnn")
        val expectedPagingData = PagingData.empty<Article>()

        every { newsRepository.searchNews(searchQuery = searchQuery, sources = sources) } returns flowOf(expectedPagingData)

        searchNews(searchQuery = searchQuery, sources = sources).test {
            assertNotNull(awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { newsRepository.searchNews(searchQuery = searchQuery, sources = sources) }
    }
}
