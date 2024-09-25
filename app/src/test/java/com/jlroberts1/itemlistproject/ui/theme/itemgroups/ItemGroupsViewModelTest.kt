package com.jlroberts1.itemlistproject.ui.theme.itemgroups

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jlroberts1.itemlistproject.domain.models.Item
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import com.jlroberts1.itemlistproject.presentation.itemgroups.ItemGroupsViewModel
import com.jlroberts1.itemlistproject.presentation.itemgroups.ItemGroupsViewState
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
class ItemGroupsViewModelTest {

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
    fun `items should emit distinct and listIds when successful`() = runTest {
        val mockItems = listOf(
            Item(listId = 1, id = 1, name = "Item1"),
            Item(listId = 2, id = 2, name = "Item2"),
            Item(listId = 1, id = 3, name = "Item3"),
        )
        coEvery { itemsRepository.getItems(capture(onComplete), capture(onError)) } answers {
            onComplete.captured.invoke()
            flowOf(mockItems)
        }

        val viewModel = ItemGroupsViewModel(itemsRepository)

        viewModel.items.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(emptyList<List<Item>>())
            val item2 = awaitItem()
            assertThat(item2).isEqualTo(listOf(1, 2))
        }
    }

    @Test
    fun `items should emit items sorted correctly when successful`() = runTest {
        val mockItems = listOf(
            Item(listId = 3, id = 2, name = "Item1"),
            Item(listId = 1, id = 1, name = "Item2"),
            Item(listId = 2, id = 3, name = "Item3"),
            Item(listId = 2, id = 4, name = "Item4")
        )

        coEvery { itemsRepository.getItems(capture(onComplete), capture(onError)) } answers {
            onComplete.captured.invoke()
            flowOf(mockItems)
        }

        val viewModel = ItemGroupsViewModel(itemsRepository)

        viewModel.items.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(emptyList<List<Item>>())
            val item2 = awaitItem()
            assertThat(item2).isEqualTo(listOf(1, 2, 3))
        }
    }

    @Test
    fun `loading should be false after items are received`() = runTest {
        val mockItems = listOf(
            Item(listId = 1, id = 1, name = "Item1"),
            Item(listId = 2, id = 2, name = "Item2")
        )

        coEvery { itemsRepository.getItems(capture(onComplete), capture(onError)) } answers {
            onComplete.captured.invoke()
            flowOf(mockItems)
        }

        val viewModel = ItemGroupsViewModel(itemsRepository)

        viewModel.state.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(
                ItemGroupsViewState(
                    loading = false,
                    error = null
                )
            )
        }
    }

    @Test
    fun `on error items should be empty and error message received`() = runTest {
        val errorMessage = "Error occurred while fetching items."

        coEvery { itemsRepository.getItems(capture(onComplete), capture(onError)) } answers {
            onError.captured.invoke(errorMessage)
            flowOf(emptyList())
        }

        val viewModel = ItemGroupsViewModel(itemsRepository)

        viewModel.state.test {
            val item = awaitItem()
            assertThat(item).isEqualTo(
                ItemGroupsViewState(
                    loading = false,
                    error = errorMessage
                )
            )
        }
    }
}