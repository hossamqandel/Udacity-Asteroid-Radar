package com.android.udacitynasa.feature_asteroids.domain.use_case

import com.android.udacitynasa.feature_asteroids.domain.model.Picture
import com.android.udacitynasa.feature_asteroids.domain.repository.IAsteroidRepository
import com.android.udacitynasa.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPictureOfTheDayUseCase @Inject constructor(
    private val iAsteroidRepository: IAsteroidRepository
) {

    operator fun invoke(): Flow<Resource<Picture>> {
        return iAsteroidRepository.getPictureOfTheDay()
    }
}