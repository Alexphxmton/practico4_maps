package com.example.practico4.data.remote

import com.example.practico4.data.model.LocationPoint
import com.example.practico4.data.model.Route
import retrofit2.http.*

interface ApiService {


    @GET("api/routes/{username}")
    suspend fun getRoutesByUser(@Path("username") username: String): List<Route>

    @POST("api/routes")
    suspend fun insertRoute(@Body route: Route): Route

    @PUT("api/routes/{id}")
    suspend fun updateRoute(@Path("id") id: Int, @Body route: Route): Route

    @DELETE("api/routes/{id}")
    suspend fun deleteRoute(@Path("id") id: Int)


    @GET("api/routes/{routeId}/locations")
    suspend fun getPoints(@Path("routeId") routeId: Int): List<LocationPoint>

    @POST("api/locations")
    suspend fun insertPoint(@Body point: LocationPoint): LocationPoint

    @PUT("api/locations/{id}")
    suspend fun updatePoint(@Path("id") id: Int, @Body point: LocationPoint): LocationPoint

    @DELETE("api/locations/{id}")
    suspend fun deletePoint(@Path("id") id: Int)
}
