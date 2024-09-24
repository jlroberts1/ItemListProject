package com.jlroberts1.itemlistproject.data.repository

import com.jlroberts1.itemlistproject.data.api.ItemsAPI
import com.jlroberts1.itemlistproject.di.IoDispatcher
import com.jlroberts1.itemlistproject.domain.models.asDomain
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class ItemsRepositoryImpl @Inject constructor(
    private val itemsAPI: ItemsAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ItemsRepository {
    override fun getItems(onComplete: () -> Unit, onError: (String?) -> Unit) = flow {
        itemsAPI.getItems()
            .suspendOnSuccess { emit(data.asDomain()) }
            .onFailure { onError("An error occurred") }
    }.onCompletion { onComplete() }
        .flowOn(ioDispatcher)
}