package com.android.udacitynasa.feature_asteroids.domain.use_case

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.android.udacitynasa.data.local.NasaDao
import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid
import com.android.udacitynasa.feature_asteroids.domain.repository.IAsteroidRepository
import com.android.udacitynasa.feature_asteroids.presentation.asteroids.OrderEvent
import com.android.udacitynasa.utils.*
import com.android.udacitynasa.utils.Const.EXCEPTION
import com.android.udacitynasa.utils.Const.EXCEPTION_IO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAsteroidsUseCase @Inject constructor(
    private val iAsteroidRepository: IAsteroidRepository,
    private val dao: NasaDao
) {

    @RequiresApi(Build.VERSION_CODES.N)
    operator fun invoke(orderEvent: OrderEvent): Flow<Resource<List<Asteroid>>> = flow {
        emit(Resource.Loading())

        var bySaved: List<Asteroid> = emptyList()
        var byToday: List<Asteroid>
        var byWeek: List<Asteroid>

        val asteroidAsync = CoroutineScope(Dispatchers.IO).async(start = CoroutineStart.LAZY) {
            iAsteroidRepository.getAsteroids()
            bySaved = dao.getAllAsteroids()
        }

        asteroidAsync.await()

        try {
            emit(Resource.Success(bySaved))
            when (orderEvent) {
                is OrderEvent.SavedOrder -> {
                    emit(Resource.Success(data = bySaved))
                    return@flow
                }

                is OrderEvent.TodayOrder -> {
                    withContext(Dispatchers.IO) {
                        byToday = dao.getAsteroidsBySpecificDateOrder(getCurrentDay(), getCurrentDay())
                    }
                    emit(Resource.Success(data = byToday))
                    return@flow
                }

                is OrderEvent.WeekOrder -> {
                    withContext(Dispatchers.IO) {
                        byWeek =
                            dao.getAsteroidsBySpecificDateOrder(getCurrentDay(), getAllWeekDays())
                    }
                    emit(Resource.Success(data = byWeek))
                    return@flow
                }
            }

        } catch (e: IOException) {
            Log.e(TAG, "invoke: ${e.localizedMessage ?: e.toString()}")
            emit(Resource.Error(EXCEPTION_IO, data = bySaved))
            return@flow
        } catch (e: HttpException) {
            Log.e(TAG, "invoke: ${e.localizedMessage ?: e.toString()}")
            emit(Resource.Error(EXCEPTION, data = bySaved))
            return@flow
        } catch (e: Exception) {
            Log.e(TAG, "invoke: ${e.localizedMessage ?: e.toString()}")
            emit(Resource.Error(EXCEPTION, data = bySaved))
            return@flow
        }
    }

}