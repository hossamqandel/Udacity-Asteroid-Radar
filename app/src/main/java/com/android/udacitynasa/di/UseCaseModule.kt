package com.android.udacitynasa.di

import com.android.udacitynasa.data.local.NasaDatabase
import com.android.udacitynasa.feature_asteroids.domain.repository.IAsteroidRepository
import com.android.udacitynasa.feature_asteroids.domain.use_case.GetAsteroidsUseCase
import com.android.udacitynasa.feature_asteroids.domain.use_case.GetPictureOfTheDayUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetPictureOfTheDay(iAsteroidRepository: IAsteroidRepository): GetPictureOfTheDayUseCase {
        return GetPictureOfTheDayUseCase(iAsteroidRepository)
    }



    @Provides
    fun provideGetAsteroids(iAsteroidRepository: IAsteroidRepository, nasaDatabase: NasaDatabase): GetAsteroidsUseCase {
        return GetAsteroidsUseCase(iAsteroidRepository, nasaDatabase.getDao())
    }

}