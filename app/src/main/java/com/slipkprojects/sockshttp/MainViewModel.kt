package com.slipkprojects.sockshttp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _status = MutableStateFlow("Disconnected")
    val status: StateFlow<String> = _status

    fun onConnectClick() {
        if (_status.value == "Disconnected") {
            _status.value = "Connected"
        } else {
            _status.value = "Disconnected"
        }
    }
}
