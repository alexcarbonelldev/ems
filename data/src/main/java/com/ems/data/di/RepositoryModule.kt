package com.ems.data.di

import com.ems.data.session.repository.SessionRepositoryImpl
import com.ems.domain.session.repository.SessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindEnergyRepository(energyRepositoryImpl: SessionRepositoryImpl): SessionRepository
}
