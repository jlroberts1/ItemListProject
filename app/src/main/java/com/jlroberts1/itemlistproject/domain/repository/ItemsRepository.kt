package com.jlroberts1.itemlistproject.domain.repository

import com.jlroberts1.itemlistproject.domain.models.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItems(onComplete: () -> Unit, onError: (String?) -> Unit): Flow<List<Item>>
}