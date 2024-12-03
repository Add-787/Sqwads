package com.psyluckco.firebase.di

import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.RoomService
import com.psyluckco.firebase.impl.AccountServiceImpl
import com.psyluckco.firebase.impl.RoomServiceImpl
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

}