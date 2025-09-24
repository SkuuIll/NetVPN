package com.slipkprojects.sockshttp

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    status: String,
    onConnectClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NetVPN") }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = status, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onConnectClick) {
                Text(text = "Connect")
            }
        }
    }
}
