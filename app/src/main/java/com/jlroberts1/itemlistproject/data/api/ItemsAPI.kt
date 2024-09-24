package com.jlroberts1.itemlistproject.data.api

import com.jlroberts1.itemlistproject.data.response.ItemDTO
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ItemsAPI {

    @GET("hiring.json")
    suspend fun getItems(): ApiResponse<List<ItemDTO>>
}