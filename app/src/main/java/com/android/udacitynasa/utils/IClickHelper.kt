package com.android.udacitynasa.utils

import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid

interface IClickHelper {

    fun onItemClick(asteroid: Asteroid)
}

