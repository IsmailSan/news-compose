package com.example.newscompose.e2e

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.example.newscompose.R
import com.example.newscompose.presentation.bookmark.BookmarkScreen
import com.example.newscompose.presentation.bookmark.BookmarkState
import com.example.newscompose.presentation.news_navigator.components.BottomNavigationItem
import com.example.newscompose.presentation.news_navigator.components.NewsBottomNavigation
import com.example.newscompose.presentation.search.SearchEvent
import com.example.newscompose.presentation.search.SearchScreen
import com.example.newscompose.presentation.search.SearchState
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalMaterial3Api::class)
class NewsAppEndToEndTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun endToEnd_userNavigationFlowFromSearchToBookmark() {
        var selectedTabIndex by mutableStateOf(1)
        var searchQuery by mutableStateOf("")
        var isSearchSubmitted by mutableStateOf(false)

        val bottomNavigationItems = listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )

        composeTestRule.setContent {
            NewsComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NewsBottomNavigation(
                            items = bottomNavigationItems,
                            selectedItem = selectedTabIndex,
                            onItemClick = { index ->
                                selectedTabIndex = index
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (selectedTabIndex) {
                            1 -> {
                                SearchScreen(
                                    state = SearchState(searchQuery = searchQuery),
                                    event = { event ->
                                        when (event) {
                                            is SearchEvent.UpdateSearchQuery -> searchQuery = event.searchQuery
                                            is SearchEvent.SearchNews -> isSearchSubmitted = true
                                        }
                                    },
                                    navigateToDetails = {}
                                )
                            }
                            2 -> {
                                BookmarkScreen(
                                    state = BookmarkState(articles = emptyList()),
                                    navigateToDetails = {}
                                )
                            }
                        }
                    }
                }
            }
        }

        // Step 1: Verify Search bar placeholder is displayed
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()

        // Step 2: User types search query "Bitcoin"
        composeTestRule.onNodeWithText("Search").performTextInput("Bitcoin")
        assertEquals("Bitcoin", searchQuery)

        // Step 3: User presses Search action on virtual keyboard (IME Search)
        composeTestRule.onNodeWithText("Bitcoin").performImeAction()
        assertTrue(isSearchSubmitted)

        // Step 4: User clicks Bookmark tab on Floating Bottom Navigation (Index 2)
        composeTestRule.onNodeWithContentDescription("Bookmark").performClick()

        // Step 5: Wait for UI transition and verify Bookmark tab screen
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Bookmark").assertIsDisplayed()
    }
}
