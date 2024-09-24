package com.jlroberts1.itemlistproject.di

import com.jlroberts1.itemlistproject.data.repository.ItemsRepositoryImpl
import com.jlroberts1.itemlistproject.domain.repository.ItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindItemRepository(itemRepositoryImpl: ItemsRepositoryImpl): ItemsRepository
}