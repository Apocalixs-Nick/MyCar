package com.example.mycar.network.fuel

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL =
    "https://raw.githubusercontent.com/Apocalixs-Nick/type-fuel-car.json/main/"

var gsonFuel: Gson = GsonBuilder()
    .setLenient()
    .create()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gsonFuel))
        .build()

interface FuelApiService {
    /**
     * Returns a [List] of [FuelInfo] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "year" endpoint will be requested with the GET
     * HTTP method
     */
    @Headers("Content-Type: application/json")
    @GET("type-fuel-car.json")
    suspend fun getFuelInfo(): List<FuelInfo>
}
/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object FuelApi {
    val retrofitService: FuelApiService by lazy { retrofit.create(FuelApiService::class.java) }
}
