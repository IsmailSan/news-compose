package com.example.newscompose.presentation.news_navigator.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.newscompose.R
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NewsBottomNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun newsBottomNavigation_displaysItemsAndTriggersOnClick() {
        var selectedIndex = 0

        val items = listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )

        composeTestRule.setContent {
            NewsComposeTheme {
                NewsBottomNavigation(
                    items = items,
                    selectedItem = selectedIndex,
                    onItemClick = { selectedIndex = it }
                )
            }
        }

        // Verify that all navigation icons are displayed on the screen
        composeTestRule.onNodeWithContentDescription("Home").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Bookmark").assertIsDisplayed()

        // Perform click on Search icon (index 1) and verify callback
        composeTestRule.onNodeWithContentDescription("Search").performClick()
        assertEquals(1, selectedIndex)

        // Perform click on Bookmark icon (index 2) and verify callback
        composeTestRule.onNodeWithContentDescription("Bookmark").performClick()
        assertEquals(2, selectedIndex)
    }
}
