/**
 * Created by developer on 05-11-2024.
 * Tismo Technology Solutions (P) Ltd.
 * developers@tismotech.net
 */

package com.psyluckco.sqwads.core.data.di

import com.psyluckco.sqwads.core.data.repository.AuthenticationRepository
import com.psyluckco.sqwads.core.data.repository.impl.AuthenticationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun provideAuthenticationRepo(impl: AuthenticationRepositoryImpl): AuthenticationRepository

}