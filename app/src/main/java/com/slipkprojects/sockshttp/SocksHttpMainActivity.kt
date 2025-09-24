package com.slipkprojects.sockshttp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class SocksHttpMainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val status by viewModel.status.collectAsState()
                MainScreen(
                    status = status,
                    onConnectClick = { viewModel.onConnectClick() }
                )
            }
        }
    }
}
