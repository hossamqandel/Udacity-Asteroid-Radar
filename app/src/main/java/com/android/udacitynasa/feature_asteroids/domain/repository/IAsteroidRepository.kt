package com.android.udacitynasa.feature_asteroids.domain.repository

import com.android.udacitynasa.feature_asteroids.domain.model.Picture
import com.android.udacitynasa.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IAsteroidRepository {

    fun getPictureOfTheDay(): Flow<Resource<Picture>>
    suspend fun getAsteroids()
}