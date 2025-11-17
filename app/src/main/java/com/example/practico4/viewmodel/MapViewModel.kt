package com.example.practico4.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practico4.data.model.LocationPoint
import com.example.practico4.data.repository.RoutesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: RoutesRepository = RoutesRepository()
) : ViewModel() {

    var savedPoints by mutableStateOf<List<LocationPoint>>(emptyList())
        private set

    var tempPoint by mutableStateOf<LatLng?>(null)
        private set

    private var currentRouteId: Int = 0

    fun loadPoints(routeId: Int) {
        currentRouteId = routeId
        viewModelScope.launch {
            savedPoints = repository.getPoints(routeId)
        }
    }

    fun selectTempPoint(latLng: LatLng) {
        tempPoint = latLng
    }

    fun saveTempPoint() {
        val tmp = tempPoint ?: return

        viewModelScope.launch {
            repository.addPoint(
                LocationPoint(
                    id = null,
                    latitude = tmp.latitude.toString(),
                    longitude = tmp.longitude.toString(),
                    routeId = currentRouteId
                )
            )


            savedPoints = repository.getPoints(currentRouteId)


            tempPoint = null
        }
    }


    fun deletePoint(pointId: Int) {
        viewModelScope.launch {
            repository.deletePoint(pointId)
            savedPoints = repository.getPoints(currentRouteId)
        }
    }
}
