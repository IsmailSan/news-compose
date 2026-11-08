package com.example.newscompose.presentation.search

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchScreen_displaysSearchBarAndHandlesQueryUpdate() {
        var searchQuery = ""
        val state = SearchState(searchQuery = searchQuery)

        composeTestRule.setContent {
            NewsComposeTheme {
                SearchScreen(
                    state = state,
                    event = { event ->
                        if (event is SearchEvent.UpdateSearchQuery) {
                            searchQuery = event.searchQuery
                        }
                    },
                    navigateToDetails = {}
                )
            }
        }

        // Verify search placeholder is displayed
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()

        // Simulate typing search query
        composeTestRule.onNodeWithText("Search").performTextInput("Bitcoin")
        assertEquals("Bitcoin", searchQuery)
    }
}
