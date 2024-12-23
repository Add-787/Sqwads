package com.psyluckco.firebase.di

import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.AnalyticsService
import com.psyluckco.firebase.RoomService
import com.psyluckco.firebase.UserRepository
import com.psyluckco.firebase.impl.AccountServiceImpl
import com.psyluckco.firebase.impl.AnalyticsServiceImpl
import com.psyluckco.firebase.impl.RoomServiceImpl
import com.psyluckco.firebase.impl.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    fun provideRoomService(impl: RoomServiceImpl): RoomService

    @Binds
    fun provideUserRepository(impl: UserDataSource): UserRepository

    @Binds
    fun provideAnalyticsService(impl: AnalyticsServiceImpl): AnalyticsService

}