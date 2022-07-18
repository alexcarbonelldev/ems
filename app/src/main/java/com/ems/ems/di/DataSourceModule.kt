package com.ems.ems.di

import com.ems.data.session.datasource.network.SessionApiDataSource
import com.ems.data.session.datasource.network.SessionRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @Binds
    fun bindEnergyRemoteDataSource(sessionApiDataSource: SessionApiDataSource): SessionRemoteDataSource
}
