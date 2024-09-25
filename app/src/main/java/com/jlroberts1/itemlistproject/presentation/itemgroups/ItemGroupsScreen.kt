package com.jlroberts1.itemlistproject.presentation.itemgroups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jlroberts1.itemlistproject.presentation.composables.GroupListItem
import com.jlroberts1.itemlistproject.presentation.composables.LoadingIndicator

@Composable
fun ItemGroupsScreen(
    onNavigateToItems: (Int) -> Unit,
    viewModel: ItemGroupsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.loading) {
            LoadingIndicator()
        }
        val items = viewModel.items.collectAsStateWithLifecycle(emptyList())
        val lazyListState = rememberLazyGridState()
        LazyVerticalGrid(
            state = lazyListState,
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.value) { item ->
                GroupListItem(
                    groupId = item,
                    onNavigateToItems = onNavigateToItems
                )
            }
        }
    }
}


