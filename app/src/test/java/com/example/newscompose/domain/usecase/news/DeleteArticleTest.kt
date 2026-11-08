package com.example.newscompose.domain.usecase.news

import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.domain.repository.NewsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeleteArticleTest {

    private val newsRepository: NewsRepository = mockk(relaxed = true)
    private lateinit var deleteArticle: DeleteArticle

    @Before
    fun setUp() {
        deleteArticle = DeleteArticle(newsRepository)
    }

    @Test
    fun `deleteArticle calls newsRepository deleteArticle`() = runTest {
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

        deleteArticle(article)

        coVerify(exactly = 1) { newsRepository.deleteArticle(article) }
    }
}
