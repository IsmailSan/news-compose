package com.example.newscompose.data.repository

import app.cash.turbine.test
import com.example.newscompose.data.local.NewsDao
import com.example.newscompose.data.remote.NewsApi
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsRepositoryImplTest {

    private val newsApi: NewsApi = mockk()
    private val newsDao: NewsDao = mockk(relaxed = true)
    private lateinit var newsRepository: NewsRepositoryImpl

    @Before
    fun setUp() {
        newsRepository = NewsRepositoryImpl(newsApi, newsDao)
    }

    @Test
    fun `upsertArticle calls newsDao upsert`() = runTest {
        val article = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = "2023-01-01",
            source = Source(id = "bbc", name = "BBC"),
            title = "Title",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg"
        )

        newsRepository.upsertArticle(article)

        coVerify(exactly = 1) { newsDao.upsert(article) }
    }

    @Test
    fun `deleteArticle calls newsDao delete`() = runTest {
        val article = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = "2023-01-01",
            source = Source(id = "bbc", name = "BBC"),
            title = "Title",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg"
        )

        newsRepository.deleteArticle(article)

        coVerify(exactly = 1) { newsDao.delete(article) }
    }

    @Test
    fun `getArticles returns flow of articles from newsDao`() = runTest {
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

        every { newsDao.getArticles() } returns flowOf(articles)

        newsRepository.getArticles().test {
            assertEquals(articles, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getArticle returns article by url from newsDao`() = runTest {
        val url = "https://example.com/article"
        val expectedArticle = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = "2023-01-01",
            source = Source(id = "bbc", name = "BBC"),
            title = "Title",
            url = url,
            urlToImage = "https://example.com/image.jpg"
        )

        coEvery { newsDao.getArticle(url = url) } returns expectedArticle

        val result = newsRepository.getArticle(url = url)

        assertEquals(expectedArticle, result)
    }
}
