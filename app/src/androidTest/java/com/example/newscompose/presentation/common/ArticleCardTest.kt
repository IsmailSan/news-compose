package com.example.newscompose.presentation.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class ArticleCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun articleCard_displaysTitleAndSourceAndHandlesClick() {
        var isClicked = false

        val article = Article(
            author = "BBC Author",
            content = "Article Content",
            description = "Article Description",
            publishedAt = "2 hours ago",
            source = Source(id = "bbc-news", name = "BBC News"),
            title = "Bitcoin Reaches Record High",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg"
        )

        composeTestRule.setContent {
            NewsComposeTheme {
                ArticleCard(
                    article = article,
                    onClick = { isClicked = true }
                )
            }
        }

        // Verify article title is displayed on screen
        composeTestRule.onNodeWithText("Bitcoin Reaches Record High").assertIsDisplayed()

        // Verify source name is displayed on screen
        composeTestRule.onNodeWithText("BBC News").assertIsDisplayed()

        // Verify published date is displayed on screen
        composeTestRule.onNodeWithText("2 hours ago").assertIsDisplayed()

        // Simulate article card click and verify onClick callback is triggered
        composeTestRule.onNodeWithText("Bitcoin Reaches Record High").performClick()
        assertTrue(isClicked)
    }
}
