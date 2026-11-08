package com.example.newscompose.presentation.bookmark

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.newscompose.domain.model.Article
import com.example.newscompose.domain.model.Source
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Rule
import org.junit.Test

class BookmarkScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bookmarkScreen_displaysTitleAndArticles() {
        val article = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = "2 hours ago",
            source = Source(id = "bbc", name = "BBC News"),
            title = "Saved Bookmark Article Title",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg"
        )
        val state = BookmarkState(articles = listOf(article))

        composeTestRule.setContent {
            NewsComposeTheme {
                BookmarkScreen(
                    state = state,
                    navigateToDetails = {}
                )
            }
        }

        // Verify "Bookmark" header title is displayed
        composeTestRule.onNodeWithText("Bookmark").assertIsDisplayed()

        // Verify bookmarked article title is displayed
        composeTestRule.onNodeWithText("Saved Bookmark Article Title").assertIsDisplayed()
    }
}
