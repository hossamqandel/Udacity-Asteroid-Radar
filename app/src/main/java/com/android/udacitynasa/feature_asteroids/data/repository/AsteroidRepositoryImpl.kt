package com.android.udacitynasa.feature_asteroids.data.repository

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.udacitynasa.data.local.NasaDao
import com.android.udacitynasa.data.remote.INasaAPI
import com.android.udacitynasa.feature_asteroids.domain.model.Picture
import com.android.udacitynasa.feature_asteroids.domain.repository.IAsteroidRepository
import com.android.udacitynasa.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AsteroidRepositoryImpl @Inject constructor(
    private val api: INasaAPI,
    private val dao: NasaDao
) : IAsteroidRepository {


    override fun getPictureOfTheDay(): Flow<Resource<Picture>> = flow {
        emit(Resource.Loading())
        val oldPictureOfDay = dao.getPictureOfDay() //Get old Pic first
        emit(Resource.Loading(data = oldPictureOfDay)) //show it to user

        delay(2500L) // give the chance to show the old image before the api request called the new image if there is a new one
        try {
            val remotePictureOfDay = api.getImageOfDay() //Get newPic From API
            if (remotePictureOfDay.media_type == "image") { //We Must to check if it's an image or not because if it's not we should not remove current saved image in database
                dao.deleteOldPictureOfDay()
                dao.insertPictureOfTheDay(remotePictureOfDay)
            }

        } catch (io: IOException) {
            Log.e(TAG, "invoke: ${io.localizedMessage ?: io.toString()}")
            emit(
                Resource.Error(
                    message = "Couldn't reach the server, please check your internet connection",
                    data = oldPictureOfDay
                )
            )
        } catch (http: HttpException) {
            Log.e(TAG, "invoke: ${http.localizedMessage ?: http.toString()}")
            emit(
                Resource.Error(
                    message = "An unexpected error occurred",
                    data = oldPictureOfDay
                )
            )
        }

        val newPictureOfDay = dao.getPictureOfDay() //Get the new image from DB
        emit(Resource.Success(newPictureOfDay)) //Show it to the USER
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAsteroids() {
        try {
            withContext(Dispatchers.IO) {
                val remoteAsteroids = api.getAsteroidsAsync(getCurrentDay(), getAllWeekDays()).await()
                val dataAsParsed = parseAsteroidsJsonResult(JSONObject(remoteAsteroids.string()))
                dao.deleteAllPreviousDayAsteroids(getCurrentDay())
                dao.insertAll(*dataAsParsed.toEntityModel())
            }

            return

        } catch (io: IOException) {
            Log.e(TAG, "getAllAsteroids: ${io.localizedMessage ?: io.toString()}")
            return
        } catch (http: HttpException) {
            Log.e(TAG, "getAllAsteroids: ${http.message ?: http.toString()}")
            return
        } catch (e: Exception) {
            Log.e(TAG, "getAllAsteroids: ${e.message ?: e.toString()}")
            return
        }
    }

}