package com.android.udacitynasa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.udacitynasa.feature_asteroids.data.local.LocaleAsteroid
import com.android.udacitynasa.feature_asteroids.domain.model.Picture


private const val VERSION = 16
@Database(entities = [LocaleAsteroid::class, Picture::class], version = VERSION)
abstract class NasaDatabase : RoomDatabase() {

    abstract fun getDao(): NasaDao

    companion object {
        const val DATABASE_NAME = "nasa database"
    }


}