package com.jlroberts1.itemlistproject.presentation.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jlroberts1.itemlistproject.presentation.composables.LoadingIndicator
import com.jlroberts1.itemlistproject.presentation.composables.OneListItem

@Composable
fun ItemsScreen(
    listId: Int,
    viewModel: ItemsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = listId) {
        viewModel.setListId(listId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.loading) {
            LoadingIndicator()
        }
        val items = viewModel.items.collectAsStateWithLifecycle(emptyList())
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(items.value) { item ->
                OneListItem(
                    itemId = item.id,
                    name = item.name ?: ""
                )
            }
        }
    }
}