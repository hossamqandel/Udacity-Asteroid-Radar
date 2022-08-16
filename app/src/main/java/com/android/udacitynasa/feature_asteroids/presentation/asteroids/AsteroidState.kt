package com.android.udacitynasa.feature_asteroids.presentation.asteroids

import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid
import com.android.udacitynasa.feature_asteroids.domain.model.Picture

data class AsteroidState(
    val isLoading: Boolean = false,
    val pictureOfDay: Picture? = null,
    val asteroids: List<Asteroid> = emptyList()
)
