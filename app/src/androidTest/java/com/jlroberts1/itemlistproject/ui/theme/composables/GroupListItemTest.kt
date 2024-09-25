package com.jlroberts1.itemlistproject.ui.theme.composables

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.jlroberts1.itemlistproject.presentation.composables.GroupListItem
import org.junit.Rule
import org.junit.Test

class GroupListItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun groupListItem_displaysCorrectText() {
        val groupId = 1
        val onNavigateToItems: (Int) -> Unit = {}

        composeTestRule.setContent {
            GroupListItem(
                groupId = groupId,
                onNavigateToItems = onNavigateToItems
            )
        }

        composeTestRule
            .onNodeWithText("Group $groupId")
            .assertIsDisplayed()
    }

    @Test
    fun groupListItem_clickNavigatesToItems() {
        val groupId = 1
        var navigatedGroupId: Int? = null
        val onNavigateToItems: (Int) -> Unit = { id -> navigatedGroupId = id }

        composeTestRule.setContent {
            GroupListItem(
                groupId = groupId,
                onNavigateToItems = onNavigateToItems
            )
        }

        composeTestRule
            .onNode(hasClickAction())
            .performClick()

        assert(navigatedGroupId == groupId) { "Expected $groupId, but was $navigatedGroupId" }
    }
}