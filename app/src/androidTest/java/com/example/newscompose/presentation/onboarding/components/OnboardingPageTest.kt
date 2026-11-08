package com.example.newscompose.presentation.onboarding.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.newscompose.R
import com.example.newscompose.presentation.onboarding.Page
import com.example.newscompose.ui.theme.NewsComposeTheme
import org.junit.Rule
import org.junit.Test

class OnboardingPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun onboardingPage_displaysTitleAndDescription() {
        val page = Page(
            title = "Lorem Ipsum Title",
            description = "Lorem Ipsum Description",
            image = R.drawable.ic_logo
        )

        composeTestRule.setContent {
            NewsComposeTheme {
                OnBoardingPage(page = page)
            }
        }

        // Verify title and description are displayed
        composeTestRule.onNodeWithText("Lorem Ipsum Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lorem Ipsum Description").assertIsDisplayed()
    }
}
