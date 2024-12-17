package com.psyluckco.google.di

import com.psyluckco.google.GoogleAuthService
import com.psyluckco.google.impl.GoogleAuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface  ServiceModule{

    @Binds
    fun provideGoogleAuth(impl: GoogleAuthServiceImpl) : GoogleAuthService
}