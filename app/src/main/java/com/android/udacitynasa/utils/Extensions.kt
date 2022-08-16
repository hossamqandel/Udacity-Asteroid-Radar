package com.android.udacitynasa.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.android.udacitynasa.feature_asteroids.data.local.LocaleAsteroid
import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.text.SimpleDateFormat
import java.util.*


val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentDay(): String {
    val calendar = Calendar.getInstance()
    return formatDate(calendar.time)
}

@RequiresApi(Build.VERSION_CODES.N)
fun getAllWeekDays(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    return formatDate(calendar.time)
}

@SuppressLint("WeekBasedYear")
@RequiresApi(Build.VERSION_CODES.N)
private fun formatDate(date: Date): String {
    val dateFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat(Const.API_QUERY_DATE_FORMAT, Locale.getDefault())
    } else {
        TODO("VERSION.SDK_INT < N")
    }
    return dateFormat.format(date)
}


fun ArrayList<Asteroid>.toEntityModel(): Array<LocaleAsteroid> {
    return map {
        LocaleAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun ImageView.setSvgColor(@ColorRes color: Int) =
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)


fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

