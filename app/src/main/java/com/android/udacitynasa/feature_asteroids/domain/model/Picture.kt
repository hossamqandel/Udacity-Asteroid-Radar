package com.android.udacitynasa.feature_asteroids.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Picture(
    val copyright: String = "",
    val date: String = "",
    val explanation: String = "",
    val hdurl: String = "",
    val media_type: String = "",
    val service_version: String = "",
    val title: String = "",
    val url: String = "",
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1
)