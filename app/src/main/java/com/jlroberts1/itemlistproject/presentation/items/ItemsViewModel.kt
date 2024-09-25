package com.jlroberts1.itemlistproject.presentation.items

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
            it.filter { item -> item.listId == listId && !item.name.isNullOrBlank() }
                .sortedWith { first, second -> compareNames(first.name!!, second.name!!) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    fun setListId(listId: Int) {
        this.listId.update { listId }
    }

    private fun compareNames(first: String, second: String): Int {
        val name1Parts = splitNamesIntoSegments(first)
        val name2Parts = splitNamesIntoSegments(second)

        for (i in name1Parts.indices) {
            if (i > name2Parts.size) return 1
            val compare = compareSegments(name1Parts[i], name2Parts[i])
            if (compare != 0) return compare
        }
        return name1Parts.size.compareTo(name2Parts.size)
    }


    private fun splitNamesIntoSegments(name: String): List<String> {
        val segments = mutableListOf<String>()
        val curr = StringBuilder()
        var isDigitSegment = name[0].isDigit()

        for (char in name) {
            if (char.isDigit() == isDigitSegment) {
                curr.append(char)
            } else {
                segments.add(curr.toString())
                curr.clear()
                curr.append(char)
                isDigitSegment = char.isDigit()
            }
        }

        segments.add(curr.toString())
        return segments
    }

    private fun compareSegments(seg1: String, seg2: String): Int {
        val isSeg1Numeric = seg1.all { it.isDigit() }
        val isSeg2Numeric = seg2.all { it.isDigit() }
        return when(isSeg1Numeric && isSeg2Numeric) {
            true -> seg1.toInt().compareTo(seg2.toInt())
            false -> seg1.compareTo(seg2)
        }
    }
}