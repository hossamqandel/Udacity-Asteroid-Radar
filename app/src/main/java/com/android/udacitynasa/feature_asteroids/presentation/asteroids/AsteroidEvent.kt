package com.android.udacitynasa.feature_asteroids.presentation.asteroids

sealed class AsteroidEvent {
    object PictureOfTheDay: AsteroidEvent()
    data class Asteroids(val orderEvent: OrderEvent): AsteroidEvent()
}
