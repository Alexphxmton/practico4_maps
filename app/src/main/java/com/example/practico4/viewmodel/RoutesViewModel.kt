package com.example.practico4.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practico4.data.model.Route
import com.example.practico4.data.repository.RoutesRepository
import kotlinx.coroutines.launch

class RoutesViewModel(
    private val repository: RoutesRepository = RoutesRepository()
) : ViewModel() {

    var routes by mutableStateOf<List<Route>>(emptyList())

    fun loadRoutes(username: String) {
        viewModelScope.launch {
            try {
                routes = repository.getRoutes(username)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createRoute(name: String, username: String) {
        viewModelScope.launch {
            try {
                repository.createRoute(
                    Route(
                        name = name,
                        username = username
                    )
                )
                loadRoutes(username)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateRoute(id: Int, newName: String, username: String) {
        viewModelScope.launch {
            try {
                repository.updateRoute(
                    id,
                    Route(
                        id = id,
                        name = newName,
                        username = username
                    )
                )
                loadRoutes(username)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteRoute(id: Int, username: String) {
        viewModelScope.launch {
            try {
                repository.deleteRoute(id)
                loadRoutes(username)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
