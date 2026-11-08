package com.example.newscompose.data.local

import com.example.newscompose.domain.model.Source
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsTypeConvertorTest {

    private lateinit var typeConvertor: NewsTypeConvertor

    @Before
    fun setUp() {
        typeConvertor = NewsTypeConvertor()
    }

    @Test
    fun `sourceToString converts Source object to comma separated string`() {
        val source = Source(id = "bbc-news", name = "BBC News")
        val result = typeConvertor.sourceToString(source)
        assertEquals("bbc-news,BBC News", result)
    }

    @Test
    fun `stringToSource converts comma separated string to Source object`() {
        val sourceString = "bbc-news,BBC News"
        val result = typeConvertor.stringToSource(sourceString)
        assertEquals("bbc-news", result.id)
        assertEquals("BBC News", result.name)
    }
}
