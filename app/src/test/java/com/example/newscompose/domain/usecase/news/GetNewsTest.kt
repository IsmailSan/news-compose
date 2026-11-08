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

class GetNewsTest {

    private val newsRepository: NewsRepository = mockk()
    private lateinit var getNews: GetNews

    @Before
    fun setUp() {
        getNews = GetNews(newsRepository)
    }

    @Test
    fun `getNews calls newsRepository getNews with sources`() = runTest {
        val sources = listOf("bbc-news", "cnn")
        val expectedPagingData = PagingData.empty<Article>()

        every { newsRepository.getNews(sources = sources) } returns flowOf(expectedPagingData)

        getNews(sources = sources).test {
            assertNotNull(awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { newsRepository.getNews(sources = sources) }
    }
}
