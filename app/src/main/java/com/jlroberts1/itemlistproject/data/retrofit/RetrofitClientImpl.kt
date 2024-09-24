package com.jlroberts1.itemlistproject.data.retrofit

import com.jlroberts1.itemlistproject.BuildConfig
import com.jlroberts1.itemlistproject.data.httpclient.HttpClient
import com.jlroberts1.itemlistproject.data.serializer.Serializer
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitClientImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val serializer: Serializer
) : RetrofitClient {
    override val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(serializer.moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(httpClient.client)
            .build()
    }
}