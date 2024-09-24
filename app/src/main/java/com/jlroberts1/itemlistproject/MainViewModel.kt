package com.jlroberts1.itemlistproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    itemsRepository: ItemsRepository
) : ViewModel() {

    val items = itemsRepository.getItems(
        onComplete = {},
        onError = {}
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

}