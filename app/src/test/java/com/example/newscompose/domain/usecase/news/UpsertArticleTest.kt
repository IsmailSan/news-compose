package com.example.newscompose.domain.usecase.news

import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.domain.repository.NewsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpsertArticleTest {

    private val newsRepository: NewsRepository = mockk(relaxed = true)
    private lateinit var upsertArticle: UpsertArticle

    @Before
    fun setUp() {
        upsertArticle = UpsertArticle(newsRepository)
    }

    @Test
    fun `upsertArticle calls newsRepository upsertArticle`() = runTest {
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

        upsertArticle(article)

        coVerify(exactly = 1) { newsRepository.upsertArticle(article) }
    }
}
