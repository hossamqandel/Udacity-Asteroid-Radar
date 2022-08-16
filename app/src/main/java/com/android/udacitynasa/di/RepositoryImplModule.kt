package com.android.udacitynasa.di

import com.android.udacitynasa.data.local.NasaDatabase
import com.android.udacitynasa.data.remote.INasaAPI
import com.android.udacitynasa.feature_asteroids.data.repository.AsteroidRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryImplModule {

    @Provides
    fun provideAsteroidRepositoryImpl(iNasaAPI: INasaAPI, nasaDatabase: NasaDatabase): AsteroidRepositoryImpl {
        return AsteroidRepositoryImpl(iNasaAPI, nasaDatabase.getDao())
    }


}