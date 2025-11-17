package com.example.practico4.ui.screens.routes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practico4.data.model.Route
import com.example.practico4.viewmodel.RoutesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutesScreen(
    username: String,
    onOpenRoute: (Route) -> Unit,
    viewModel: RoutesViewModel = viewModel()
) {

    var showCreateDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var currentRouteName by remember { mutableStateOf("") }
    var currentRouteId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadRoutes(username)
    }

    Column(Modifier.fillMaxSize()) {

        LazyColumn(Modifier.weight(1f)) {
            items(viewModel.routes) { route ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {

                        // NOMBRE + ABRIR
                        Text(
                            text = route.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOpenRoute(route) }
                                .padding(4.dp)
                        )

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {
                                currentRouteId = route.id
                                currentRouteName = route.name
                                showEditDialog = true
                            }) {
                                Text("Editar")
                            }

                            TextButton(onClick = {
                                currentRouteId = route.id
                                showDeleteDialog = true
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { showCreateDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Crear Ruta")
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Nueva ruta") },
            text = {
                OutlinedTextField(
                    value = currentRouteName,
                    onValueChange = { currentRouteName = it },
                    label = { Text("Nombre de la ruta") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    if (currentRouteName.isNotBlank()) {
                        viewModel.createRoute(currentRouteName, username)
                        currentRouteName = ""
                        showCreateDialog = false
                    }
                }) { Text("Crear") }
            },
            dismissButton = {
                TextButton(onClick = {
                    currentRouteName = ""
                    showCreateDialog = false
                }) { Text("Cancelar") }
            }
        )
    }


    if (showEditDialog && currentRouteId != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar ruta") },
            text = {
                OutlinedTextField(
                    value = currentRouteName,
                    onValueChange = { currentRouteName = it },
                    label = { Text("Nuevo nombre") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateRoute(
                        id = currentRouteId!!,
                        newName = currentRouteName,
                        username = username
                    )
                    showEditDialog = false
                }) {
                    Text("Guardar cambios")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEditDialog = false
                }) { Text("Cancelar") }
            }
        )
    }


    if (showDeleteDialog && currentRouteId != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar ruta") },
            text = { Text("¿Seguro que deseas eliminar esta ruta?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteRoute(currentRouteId!!, username)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
