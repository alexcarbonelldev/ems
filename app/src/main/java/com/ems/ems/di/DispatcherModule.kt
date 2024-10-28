package com.ems.ems.di

import com.ems.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object DispatcherModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher get() = Dispatchers.Main
        override val default: CoroutineDispatcher get() = Dispatchers.Default
        override val io: CoroutineDispatcher get() = Dispatchers.IO
        override val unconfined: CoroutineDispatcher get() = Dispatchers.Unconfined
    }
}
