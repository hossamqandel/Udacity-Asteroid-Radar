package com.android.udacitynasa.di

import com.android.udacitynasa.feature_asteroids.data.repository.AsteroidRepositoryImpl
import com.android.udacitynasa.feature_asteroids.domain.repository.IAsteroidRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideIAsteroidRepository(asteroidRepositoryImpl: AsteroidRepositoryImpl): IAsteroidRepository


}
