package com.example.practico4.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(onLogin: (String) -> Unit) {

    var user by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Ingrese su usuario", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = user,
            onValueChange = {
                user = it
                error = false
            },
            label = { Text("Usuario") },
            isError = error,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (error) {
            Text(
                "El usuario no puede estar vacío",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(25.dp))

        Button(
            onClick = {
                if (user.trim().isEmpty()) {
                    error = true
                } else {
                    onLogin(user.trim())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}
