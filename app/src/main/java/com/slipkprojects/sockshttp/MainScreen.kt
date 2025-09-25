package com.slipkprojects.sockshttp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings

@Composable
fun MainScreen(
    status: String,
    onConnectClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NetVPN") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Ajustes")
                    }
                    TextButton(onClick = onSettingsClick) {
                        Text("Ajustes")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val statusEs = when (status) {
                "Connected" -> "Conectado"
                "Disconnected" -> "Desconectado"
                else -> status
            }
            Text(text = statusEs, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onConnectClick) {
                Text(text = "Conectar")
            }
        }
    }
}
