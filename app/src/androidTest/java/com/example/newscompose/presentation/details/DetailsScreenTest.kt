package com.example.newscompose.presentation.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsScreen_displaysArticleTitleAndContent() {
        val article = Article(
            author = "Author",
            content = "Detailed news article content text here",
            description = "Description",
            publishedAt = "2 hours ago",
            source = Source(id = "bbc", name = "BBC News"),
            title = "Exclusive Headline Title",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg"
        )

        composeTestRule.setContent {
            NewsComposeTheme {
                DetailsScreen(
                    article = article,
                    event = {},
                    navigateUp = {}
                )
            }
        }

        // Verify article title is displayed on screen
        composeTestRule.onNodeWithText("Exclusive Headline Title").assertIsDisplayed()

        // Verify article content is displayed on screen
        composeTestRule.onNodeWithText("Detailed news article content text here").assertIsDisplayed()
    }
}
