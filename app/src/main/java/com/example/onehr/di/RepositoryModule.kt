package com.example.onehr.di

import com.example.onehr.repository.FirebaseRepository
import com.example.onehr.repository.FirebaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun getFirebaseAuthRepository(repo : FirebaseRepositoryImpl) : FirebaseRepository

}