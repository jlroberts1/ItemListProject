package com.jlroberts1.itemlistproject

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    object ItemGroupsScreen

    @Serializable
    data class ItemsScreen(
        val listId: Int
    )
}