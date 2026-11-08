package com.example.newscompose.domain.usecase.news

import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetArticleTest {

    private val newsRepository: NewsRepository = mockk()
    private lateinit var getArticle: GetArticle

    @Before
    fun setUp() {
        getArticle = GetArticle(newsRepository)
    }

    @Test
    fun `getArticle returns article by url from newsRepository`() = runTest {
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

        coEvery { newsRepository.getArticle(url = url) } returns expectedArticle

        val result = getArticle(url = url)

        assertEquals(expectedArticle, result)
    }
}
