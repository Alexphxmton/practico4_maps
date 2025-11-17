package com.example.practico4.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practico4.viewmodel.MapViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(
    routeId: Int,
    viewModel: MapViewModel = viewModel()
) {
    val savedPoints = viewModel.savedPoints
    val tempPoint = viewModel.tempPoint

    var showDeleteDialog by remember { mutableStateOf(false) }
    var pointToDelete by remember { mutableStateOf<Int?>(null) }


    LaunchedEffect(Unit) {
        viewModel.loadPoints(routeId)
    }

    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            onMapClick = { latLng ->
                viewModel.selectTempPoint(latLng)
            }
        ) {

            savedPoints.forEach { p ->

                Marker(
                    state = MarkerState(
                        position = LatLng(
                            p.latitude.toDouble(),
                            p.longitude.toDouble()
                        )
                    ),
                    title = "Punto fijo",
                    onInfoWindowLongClick = {
                        pointToDelete = p.id
                        showDeleteDialog = true
                    }
                )
            }

            tempPoint?.let { point ->
                Marker(
                    state = MarkerState(position = point),
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED
                    )
                )
            }

            if (savedPoints.size > 1) {
                Polyline(
                    points = savedPoints.map {
                        LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    }
                )
            }
        }

        if (tempPoint != null) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.saveTempPoint()
                    }
                ) {
                    Text("Agregar punto")
                }
            }
        }
    }

    if (showDeleteDialog && pointToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                pointToDelete = null
            },
            title = { Text("Eliminar punto") },
            text = { Text("¿Está seguro de que desea eliminar este punto?") },

            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deletePoint(pointToDelete!!)
                        showDeleteDialog = false
                        pointToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        pointToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
