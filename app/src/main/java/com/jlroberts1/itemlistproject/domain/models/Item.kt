package com.jlroberts1.itemlistproject.domain.models

import com.jlroberts1.itemlistproject.data.response.ItemDTO
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val listId: Int,
    val name: String?
)

fun List<ItemDTO>.asDomain() = this.map {
    Item(
        id = it.id,
        listId = it.listId,
        name = it.name
    )
}