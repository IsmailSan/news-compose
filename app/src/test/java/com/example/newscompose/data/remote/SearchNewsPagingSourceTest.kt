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

class SearchNewsPagingSourceTest {

    private val newsApi: NewsApi = mockk()
    private lateinit var searchNewsPagingSource: SearchNewsPagingSource

    @Before
    fun setUp() {
        searchNewsPagingSource = SearchNewsPagingSource(
            api = newsApi,
            searchQuery = "crypto",
            sources = "bbc-news,cnn"
        )
    }

    @Test
    fun `load returns LoadResult Page on successful API search response`() = runTest {
        val article = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = "2023-01-01",
            source = Source(id = "bbc", name = "BBC"),
            title = "Crypto News",
            url = "https://example.com/crypto",
            urlToImage = "https://example.com/image.jpg"
        )
        val response = NewsResponse(
            articles = listOf(article),
            status = "ok",
            totalResults = 1
        )

        coEvery {
            newsApi.searchNews(searchQuery = "crypto", sources = "bbc-news,cnn", page = 1)
        } returns response

        val expected = PagingSource.LoadResult.Page(
            data = listOf(article),
            prevKey = null,
            nextKey = null
        )

        val result = searchNewsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun `load returns LoadResult Error on search API error`() = runTest {
        val exception = RuntimeException("Server Error")
        coEvery {
            newsApi.searchNews(searchQuery = "crypto", sources = "bbc-news,cnn", page = 1)
        } throws exception

        val result = searchNewsPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        assertEquals(exception, (result as PagingSource.LoadResult.Error).throwable)
    }
}
