package com.android.udacitynasa.feature_asteroids.domain.model

data class NasaImage(
    val collection: Collection? = null
)

data class Collection(
    val href: String = "",
    val items: List<Any> = emptyList(),
    val metadata: Metadata? = null,
    val version: String = ""
)

data class Metadata(
    val total_hits: Int? = null
)