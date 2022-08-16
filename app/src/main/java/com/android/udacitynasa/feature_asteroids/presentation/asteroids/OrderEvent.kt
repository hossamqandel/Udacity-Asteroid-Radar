package com.android.udacitynasa.feature_asteroids.presentation.asteroids

sealed class OrderEvent {
    object WeekOrder: OrderEvent()
    object TodayOrder: OrderEvent()
    object SavedOrder: OrderEvent()
}
