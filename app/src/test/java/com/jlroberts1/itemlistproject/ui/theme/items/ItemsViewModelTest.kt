package com.jlroberts1.itemlistproject.ui.theme.items

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jlroberts1.itemlistproject.domain.models.Item
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ItemsViewModelTest {

    private val itemsRepository = mockk<ItemsRepository>()

    private val onComplete = slot<() -> Unit>()
    private val onError = slot<(String?) -> Unit>()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `items should contain the correct items after successful fetch`() = runTest {
        val mockItems = listOf(
            Item(listId = 1, id = 1, name = "Item 1"),
            Item(listId = 2, id = 2, name = "Item 2"),
            Item(listId = 1, id = 3, name = "Item 3"),
            Item(listId = 2, id = 4, name = "Item 4"),
            Item(listId = 1, id = 5, name = "Item 5")

        )

        coEvery { itemsRepository.getItems(capture(onComplete), capture(onError)) } answers {
            onComplete.captured.invoke()
            flowOf(mockItems)
        }

        val viewModel = ItemsViewModel(itemsRepository)

        val expected = listOf(
            Item(1, 1, "Item 1"),
            Item(3, 1, "Item 3"),
            Item(5, 1, "Item 5")
        )

        viewModel.items.test {
            viewModel.setListId(1)
            val item1 = awaitItem()
            assertThat(item1).isEqualTo(emptyList<Item>())
            val item2 = awaitItem()
            assertThat(item2).isEqualTo(expected)
        }
    }
}