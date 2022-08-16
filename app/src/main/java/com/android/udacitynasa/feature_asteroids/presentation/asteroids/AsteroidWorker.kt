package com.android.udacitynasa.feature_asteroids.presentation.asteroids

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.udacitynasa.R
import com.android.udacitynasa.feature_asteroids.domain.repository.IAsteroidRepository
import com.android.udacitynasa.utils.Const.NOTIFICATION_ID
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.random.Random

class AsteroidWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context, workerParams) {

    private val TAG = "AsteroidWorker"
    @Inject
    lateinit var repository: IAsteroidRepository

     companion object {
         const val WORKER_NAME = "AsteroidWorker"
     }

    override suspend fun doWork(): Result {

        return try {
            Log.d(TAG, "doWork: Success Worker Job")
            showNewWorkManagerJobNotification()
            repository.getAsteroids()
            Result.success()
        } catch (e: Exception){
            Result.failure()
        } catch (e: IOException){
            Result.retry()
        } catch (e: HttpException){
            Result.retry()
        }
    }

    private fun showNewWorkManagerJobNotification() {

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New Work Manager Task #")
            .setContentText("Work manager have been insert new data to database")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Channel_Name"
            val channelDescription = "Channel_Description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(NOTIFICATION_ID, channelName, channelImportance).apply {
                description = channelDescription
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }


        with(NotificationManagerCompat.from(applicationContext)) {
            notify(Random.nextInt(), notification.build())
        }

    }


}
