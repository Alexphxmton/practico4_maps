package com.example.practico4.data.repository

import com.example.practico4.data.model.LocationPoint
import com.example.practico4.data.model.Route
import com.example.practico4.data.remote.ApiService
import com.example.practico4.data.remote.RetrofitClient

class RoutesRepository(
    private val api: ApiService = RetrofitClient.api
) {

    suspend fun getRoutes(username: String): List<Route> {
        return api.getRoutesByUser(username)
    }

    suspend fun createRoute(route: Route): Int {
        val created = api.insertRoute(route)
        return created.id ?: 0
    }

    suspend fun updateRoute(routeId: Int, route: Route): Route {
        return api.updateRoute(routeId, route)
    }

    suspend fun deleteRoute(routeId: Int) {
        api.deleteRoute(routeId)
    }

    suspend fun getPoints(routeId: Int): List<LocationPoint> {
        return api.getPoints(routeId)
    }
    suspend fun addPoint(point: LocationPoint): LocationPoint {
        return api.insertPoint(point)
    }


    suspend fun deletePoint(pointId: Int) {
        api.deletePoint(pointId)
    }
}
