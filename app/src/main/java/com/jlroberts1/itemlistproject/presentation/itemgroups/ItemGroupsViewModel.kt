package com.jlroberts1.itemlistproject.presentation.itemgroups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ItemGroupsViewState(
    val loading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class ItemGroupsViewModel @Inject constructor(
    itemsRepository: ItemsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ItemGroupsViewState())
    val state = _state.asStateFlow()

    val items = itemsRepository.getItems(
        onComplete = { _state.update { it.copy(loading = false, error = null) } },
        onError = { error ->
            _state.update { it.copy(loading = false, error = error) }
        }
    ).map { it.map { item -> item.listId }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())
}