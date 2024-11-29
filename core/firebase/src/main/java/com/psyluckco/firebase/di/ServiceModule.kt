package com.psyluckco.firebase.di

import com.psyluckco.firebase.AccountService
import com.psyluckco.firebase.impl.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ServiceModule {

    @Binds
    fun provideAccountService(impl: AccountServiceImpl): AccountService

}