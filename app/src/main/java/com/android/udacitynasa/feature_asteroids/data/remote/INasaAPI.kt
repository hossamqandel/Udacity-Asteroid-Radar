package com.android.udacitynasa.data.remote

import com.android.udacitynasa.feature_asteroids.domain.model.NasaImage
import com.android.udacitynasa.feature_asteroids.domain.model.Picture
import com.android.udacitynasa.utils.Const
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface INasaAPI {


    @GET("neo/rest/v1/feed")
    fun getAsteroidsAsync(
        @Query("start_date") startDate: String = "2015-09-06",
        @Query("end_date") endDate: String = "2015-09-09",
        @Query("api_key") apiKey: String = Const.API_KEY
    ): Deferred<ResponseBody>

    @GET("planetary/apod")
    suspend fun getImageOfDay(
        @Query("api_key") apiKey: String = Const.API_KEY
    ): Picture

    @GET("search")
    suspend fun getNasaImageById(
        @Query("q") id: String
    ): NasaImage


}