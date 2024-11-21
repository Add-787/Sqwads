package com.psyluckco.sqwads.core.common.di

import com.psyluckco.sqwads.core.common.LogService
import com.psyluckco.sqwads.core.common.impl.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    fun provideLogService(impl : LogServiceImpl) : LogService

}