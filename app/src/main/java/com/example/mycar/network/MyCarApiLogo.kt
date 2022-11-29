package com.example.mycar.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL =
    "https://raw.githubusercontent.com/ErCrasher27/carl-maker-logos.json/main/"

var gsonLogo: Gson = GsonBuilder()
    .setLenient()
    .create()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gsonLogo))
        .build()


/**
 * A public interface that exposes the [getMyCarLogo] method
 */
interface MyCarApiLogoService {

    /**
     * Returns a [List] of [MyCarApiLogo] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "year" endpoint will be requested with the GET
     * HTTP method
     */
    @Headers("Content-Type: application/json")
    @GET("carl-maker-logos.json")
    suspend fun getMyCarLogo(): List<MyCarLogo>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MyCarApiLogo {
    val retrofitService: MyCarApiLogoService by lazy { retrofit.create(MyCarApiLogoService::class.java) }
}