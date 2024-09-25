package com.jlroberts1.itemlistproject.ui.theme.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class OneListItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun oneListItem_displaysCorrectText() {
        val itemId = 1
        val name = "Item 1"

        composeTestRule.setContent {
            OneListItem(
                itemId = itemId,
                name = name
            )
        }

        composeTestRule
            .onNodeWithText(itemId.toString())
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(name)
            .assertIsDisplayed()
    }
}