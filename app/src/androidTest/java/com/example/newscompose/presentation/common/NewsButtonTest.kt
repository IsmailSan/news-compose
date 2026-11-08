package com.example.newscompose.presentation.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NewsButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun newsButton_displaysTextAndHandlesClick() {
        var isClicked = false

        composeTestRule.setContent {
            NewsComposeTheme {
                NewsButton(
                    text = "Get Started",
                    onClick = { isClicked = true }
                )
            }
        }

        // Verify button text is displayed
        composeTestRule.onNodeWithText("Get Started").assertIsDisplayed()

        // Simulate click and verify callback
        composeTestRule.onNodeWithText("Get Started").performClick()
        assertTrue(isClicked)
    }

    @Test
    fun newsTextButton_displaysTextAndHandlesClick() {
        var isClicked = false

        composeTestRule.setContent {
            NewsComposeTheme {
                NewsTextButton(
                    text = "Skip",
                    onClick = { isClicked = true }
                )
            }
        }

        // Verify text button is displayed
        composeTestRule.onNodeWithText("Skip").assertIsDisplayed()

        // Simulate click and verify callback
        composeTestRule.onNodeWithText("Skip").performClick()
        assertTrue(isClicked)
    }
}
