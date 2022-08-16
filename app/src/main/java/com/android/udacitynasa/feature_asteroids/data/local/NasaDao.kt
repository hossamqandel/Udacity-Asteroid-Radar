package com.android.udacitynasa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.udacitynasa.feature_asteroids.data.local.LocaleAsteroid
import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid
import com.android.udacitynasa.feature_asteroids.domain.model.Picture

@Dao
interface NasaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: LocaleAsteroid)

    @Query("SELECT * FROM LocaleAsteroid ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): List<Asteroid>

    @Query("SELECT * FROM LocaleAsteroid WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsBySpecificDateOrder(startDate: String, endDate: String): List<Asteroid>


    @Query("DELETE FROM LocaleAsteroid WHERE closeApproachDate < :today")
    fun deleteAllPreviousDayAsteroids(today: String): Int



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(pictureOfDay: Picture)

    @Query("SELECT * FROM Picture")
    suspend fun getPictureOfDay(): Picture


    @Query("DELETE FROM Picture")
    suspend fun deleteOldPictureOfDay()

}