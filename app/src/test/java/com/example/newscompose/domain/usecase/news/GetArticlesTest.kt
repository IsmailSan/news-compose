package com.example.newscompose.domain.usecase.news

import app.cash.turbine.test
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.domain.repository.NewsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class  GetArticlesTest {

    private val newsRepository: NewsRepository = mockk()
    private lateinit var getArticles: GetArticles

    @Before
    fun setUp() {
        getArticles = GetArticles(newsRepository)
    }

    @Test
    fun `getArticles returns flow of articles list from newsRepository`() = runTest {
        val articles = listOf(
            Article(
                author = "Author",
                content = "Content",
                description = "Description",
                publishedAt = "2023-01-01",
                source = Source(id = "bbc", name = "BBC"),
                title = "Title",
                url = "https://example.com/article",
                urlToImage = "https://example.com/image.jpg"
            )
        )

        every { newsRepository.getArticles() } returns flowOf(articles)

        getArticles().test {
            assertEquals(articles, awaitItem())
            awaitComplete()
        }
    }
}
