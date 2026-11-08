package com.example.newscompose.presentation.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class SearchBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchBar_displaysTextAndTriggersOnValueChange() {
        var typedText = ""

        composeTestRule.setContent {
            NewsComposeTheme {
                SearchBar(
                    text = typedText,
                    readOnly = false,
                    onValueChange = { typedText = it },
                    onSearch = {}
                )
            }
        }

        // Verify search placeholder is displayed when empty
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()

        // Simulate user typing into search field and verify callback
        composeTestRule.onNodeWithText("Search").performTextInput("Crypto")
        assertEquals("Crypto", typedText)
    }
}
