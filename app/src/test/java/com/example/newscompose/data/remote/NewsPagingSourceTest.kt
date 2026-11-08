package com.example.newscompose.data.remote

import androidx.paging.PagingSource
import com.example.newscompose.data.remote.dto.NewsResponse
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NewsPagingSourceTest {

    private val newsApi: NewsApi = mockk()
    private lateinit var newsPagingSource: NewsPagingSource

    @Before
    fun setUp() {
        newsPagingSource = NewsPagingSource(
            newsApi = newsApi,
            sources = "bbc-news,cnn"
        )
    }

    @Test
    fun `load returns LoadResult Page on successful API response with distinct articles`() = runTest {
        val article1 = Article(
            author = "Author 1",
            content = "Content 1",
            description = "Description 1",
            publishedAt = "2023-01-01",
            source = Source(id = "bbc", name = "BBC"),
            title = "Duplicate Title",
            url = "https://example.com/1",
            urlToImage = "https://example.com/1.jpg"
        )
        val article2 = Article(
            author = "Author 2",
            content = "Content 2",
            description = "Description 2",
            publishedAt = "2023-01-01",
            source = Source(id = "cnn", name = "CNN"),
            title = "Duplicate Title", // Duplikat judul
            url = "https://example.com/2",
            urlToImage = "https://example.com/2.jpg"
        )
        val response = NewsResponse(
            articles = listOf(article1, article2),
            status = "ok",
            totalResults = 2
        )

        coEvery { newsApi.getNews(sources = "bbc-news,cnn", page = 1) } returns response

        val expected = PagingSource.LoadResult.Page(
            data = listOf(article1),
            prevKey = null,
            nextKey = null
        )

        val result = newsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun `load returns LoadResult Error on API failure`() = runTest {
        val exception = RuntimeException("Network Error")
        coEvery { newsApi.getNews(sources = "bbc-news,cnn", page = 1) } throws exception

        val result = newsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(exception, (result as PagingSource.LoadResult.Error).throwable)
    }
}
