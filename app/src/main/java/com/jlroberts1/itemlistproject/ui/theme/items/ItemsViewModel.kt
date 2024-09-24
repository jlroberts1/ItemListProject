package com.jlroberts1.itemlistproject.ui.theme.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ItemsViewState(
    val loading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    private val listId = MutableStateFlow<Int?>(null)
    private val _state = MutableStateFlow(ItemsViewState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val items = listId.flatMapLatest { listId ->
        itemsRepository.getItems(
            onComplete = { _state.update { it.copy(loading = false, error = null) } },
            onError = { error -> _state.update { it.copy(loading = false, error = error) } }
        ).map {
            it.filter { item ->
                item.listId == listId && !item.name.isNullOrBlank()
            }.sortedBy { item ->
                item.name?.let { name -> geItemNumbers(name) }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    fun setListId(listId: Int) {
        this.listId.value = listId
    }

    private fun geItemNumbers(name: String): Int {
        return Integer.parseInt(name.filter { chars -> chars.isDigit() })
    }
}