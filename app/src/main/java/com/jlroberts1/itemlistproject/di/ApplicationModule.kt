package com.jlroberts1.itemlistproject.di

import com.jlroberts1.itemlistproject.data.httpclient.HttpClient
import com.jlroberts1.itemlistproject.data.httpclient.HttpClientImpl
import com.jlroberts1.itemlistproject.data.retrofit.RetrofitClient
import com.jlroberts1.itemlistproject.data.retrofit.RetrofitClientImpl
import com.jlroberts1.itemlistproject.data.serializer.Serializer
import com.jlroberts1.itemlistproject.data.serializer.SerializerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideHttpClient(httpClient: HttpClientImpl): HttpClient {
        return httpClient
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(retrofitClient: RetrofitClientImpl): RetrofitClient {
        return retrofitClient
    }

    @Singleton
    @Provides
    fun provideSerializer(serializer: SerializerImpl): Serializer {
        return serializer
    }
}